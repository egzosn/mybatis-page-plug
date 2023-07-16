package com.egzosn.mybatis.page.plug;

import com.egzosn.mybatis.page.bean.Dialect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class PagingInterceptor implements Interceptor {
    /**
     * 方言
     */
    private volatile Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor){
            return Plugin.wrap(new PageExecutor((Executor)target, Dialect.mysql), this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        if (null == this.dialect){
            String dialect = properties.getProperty("dialect");
            //默认使用mysql
            if (null == dialect || "".equals(dialect)) {
                this.dialect = Dialect.mysql;
                return;
            }

            this.dialect = Dialect.valueOf(dialect);
        }

//        System.out.println("mybatis intercept dialect:"+ dialect);
    }
}
