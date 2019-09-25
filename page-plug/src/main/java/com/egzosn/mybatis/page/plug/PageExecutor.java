package com.egzosn.mybatis.page.plug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import com.egzosn.mybatis.page.bean.Dialect;
import com.egzosn.mybatis.page.bean.Page;
import com.egzosn.mybatis.page.bean.Paging;
import com.egzosn.mybatis.page.utils.FieldUtils;


/**
 * 分页执行者
 *
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/16.15:32
 */
public class PageExecutor implements Executor {


    public static final String countId = "$Count";

    private volatile Dialect dialect;

    private final Executor delegate;

    private  Object handlerObj;

    public PageExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    public PageExecutor(Executor delegate, Dialect dialect) {
        this.delegate = delegate;
        this.dialect = dialect;
    }

    public PageExecutor(Executor delegate, Dialect dialect, Object handlerObj) {
        this.delegate = delegate;
        this.dialect = dialect;
        this.handlerObj = handlerObj;
    }


    @Override
    public Transaction getTransaction() {
        return delegate.getTransaction();
    }

    @Override
    public void close(boolean forceRollback) {
        delegate.close(forceRollback);
    }

    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    @Override
    public int update(MappedStatement ms, Object parameterObject) throws SQLException {
        return delegate.update(ms, parameterObject);
    }

    public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, Paging paging) throws SQLException {
        CacheKey key = delegate.createCacheKey(ms, parameterObject, rowBounds, boundSql);
//        if (null != resultHandler) {
        return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
//        }
        /*AliasElColumnMapRowMapper handler = new AliasElColumnMapRowMapper(new ArrayList<Object>(), handlerObj);
        delegate.query(ms, parameterObject, rowBounds, handler, key, boundSql);
        return handler.getResultList();*/
    }
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameterObject);

        Paging paging = getPaging(parameterObject);
        if (null != paging) {
            return queryPaging(ms, parameterObject, rowBounds, resultHandler, boundSql, paging);
        }
        return query(ms, parameterObject, rowBounds, resultHandler, boundSql, paging);
    }


    /**
     * 获取分页信息
     *
     * @param parameterObject 参数
     * @return
     */
    public Paging getPaging(Object parameterObject) {
        if (parameterObject instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
            for (Object value : paramMap.values()) {
                if (value instanceof Paging) {
                    return (Paging) value;
                }
            }
        }
        if (parameterObject instanceof Paging) {
            return (Paging) parameterObject;
        }
        return Page.getPaging();
    }

    /**
     * 分页查询
     */
    public <E> List<E> queryPaging(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, Paging paging) throws SQLException {

        List result = new ArrayList(1);
        long num = this.queryCount(ms.getConfiguration(), ms, rowBounds, resultHandler, boundSql, paging.isRemoveSelect());
        List content = new ArrayList();
        if (0 != num) {
            if (paging.isPaging()) {
                String sql = dialect.forPaginate(boundSql.getSql(), paging.getPage(), paging.getRows());

                FieldUtils.setField(FieldUtils.findFieldCaches(BoundSql.class, "sql"), boundSql, sql);
            }
            content = query(ms, parameterObject, rowBounds, resultHandler, boundSql, paging);
        }
        result.add(new Page(paging.getPage(), paging.getRows(), num, content));
        Page.removePaging();
        return result;
    }

    /**
     * 查询记录数
     */
    public long queryCount(Configuration configuration, MappedStatement ms, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, boolean removeSelect) throws SQLException {

        //判断是否存在配置里面，不存在则动态创建一个统计的映射语句
        MappedStatement cms = getMappedStatement(configuration, ms, boundSql, removeSelect);
        List<Long> count = delegate.query(cms, boundSql.getParameterObject(), rowBounds, resultHandler);
        if (count.isEmpty()) {
            return 0L;
        }
        return count.get(0);
    }

    /**
     * 获取一个 统计的 MappedStatement
     *
     * @param configuration 配置
     * @param ms            MappedStatement的id
     * @param boundSql      BoundSql
     * @param removeSelect 是否移除select与order by 部分
     * @return 统计的 MappedStatement
     */
    private MappedStatement getMappedStatement(Configuration configuration, MappedStatement ms, BoundSql boundSql, boolean removeSelect) {
        String id = ms.getId() + countId;
        if (configuration.hasStatement(id)) {
            return configuration.getMappedStatement(id);
        }
        if (ms.getSqlSource() instanceof DynamicSqlSource){
            Object rootSqlNode = FieldUtils.getField(FieldUtils.findFieldCaches(DynamicSqlSource.class, "rootSqlNode"), ms.getSqlSource());
            if (rootSqlNode instanceof MixedSqlNode){
                List<SqlNode> tmpContents = (List<SqlNode>) FieldUtils.getField(FieldUtils.findFieldCaches(MixedSqlNode.class, "contents"), rootSqlNode);
                List<SqlNode> contents = new ArrayList<SqlNode>();
                contents.add(new StaticTextSqlNode(dialect.getCountPrefixSQL(removeSelect)));
                contents.addAll(tmpContents);
                contents.add(new StaticTextSqlNode(dialect.getCountSuffixSQL(removeSelect)));
                MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
                //数据源
                SqlSource sqlSource = new DynamicSqlSource(configuration, mixedSqlNode);
                return getMappedStatement(configuration, id, sqlSource, true);
            }
        }else if (ms.getSqlSource() instanceof RawSqlSource){
            Object tmpSource = FieldUtils.getField(FieldUtils.findFieldCaches(RawSqlSource.class, "sqlSource"), ms.getSqlSource());
            if (tmpSource instanceof StaticSqlSource){
                return getStaticSqlMappedStatement(configuration, boundSql, id, removeSelect, true);
            }
        }
        return getStaticSqlMappedStatement(configuration, boundSql, id+System.currentTimeMillis(), removeSelect, false);
    }

    /**
     * 获取一个 统计的 MappedStatement
     *
     * @param configuration 配置
     * @param boundSql      BoundSql
     * @param id      id
     * @param removeSelect 是否移除select与order by 部分
     * @return 统计的 MappedStatement
     */
    private MappedStatement getStaticSqlMappedStatement(Configuration configuration, BoundSql boundSql, String id, boolean removeSelect, boolean cache) {
        String countSql = boundSql.getSql().replace("\n", " ").replace("\t", " ").replace("\r", " ");
        SqlSource sqlSource = new StaticSqlSource(configuration, dialect.getCountSQL(countSql, removeSelect), boundSql.getParameterMappings());
        return getMappedStatement(configuration, id, sqlSource, cache);
    }
    /**
     * 获取一个 统计的 MappedStatement
     *
     * @param configuration 配置
     * @param sqlSource      sql来源
     * @param cache      是否缓存
     * @return 统计的 MappedStatement
     */
    private MappedStatement getMappedStatement(Configuration configuration, String id, SqlSource sqlSource, boolean cache) {

        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        //结果集类型定义
        resultMaps.add(new ResultMap.Builder(configuration, id + "-Inline", Long.class, new ArrayList<ResultMapping>()).build());

        MappedStatement cms = new MappedStatement.Builder(configuration, id, sqlSource, SqlCommandType.SELECT)
                .resultMaps(resultMaps)
                .build();
        if (cache){
            configuration.addMappedStatement(cms);
        }
        return cms;
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        return delegate.queryCursor(ms, parameter, rowBounds);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
            throws SQLException {
        return delegate.<E>query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return delegate.flushStatements();
    }

    @Override
    public void commit(boolean required) throws SQLException {
        delegate.commit(required);
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        delegate.rollback(required);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return delegate.createCacheKey(ms, parameterObject, rowBounds, boundSql);
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return delegate.isCached(ms, key);
    }

    @Override
    public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {
        delegate.deferLoad(ms, resultObject, property, key, targetType);
    }

    @Override
    public void clearLocalCache() {
        delegate.clearLocalCache();
    }

    @Override
    public void setExecutorWrapper(Executor executor) {
        delegate.setExecutorWrapper(executor);
    }



}
