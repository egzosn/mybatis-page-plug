package com.egzosn.mybatis.page.utils;

/**
 * Sql 处理工具
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/16.20:02
 */
public final class SqlTools {
    /**
     * 获取统计的sql
     *
     * @param sql 原始sql
     * @return 转化后的sql
     */
    public static String getCountSQL(final String sql) {

        return getCountSQL(sql, "*");
    }

    /**
     * 获取统计的sql
     *
     * @param sql        原始sql
     * @param countField 需要统计的字段
     * @return 转化后的sql
     */
    public static String getCountSQL(final String sql, String countField) {


        return getCountSQL(sql, countField, "");
    }

    /**
     * 获取统计的sql
     *
     * @param sql        原始sql
     * @param countField 需要统计的字段
     * @param alias      统计结果的别名
     * @return 转化后的sql
     */
    public static String getCountSQL(final String sql, String countField, String alias) {

        String countSql = String.format("SELECT COUNT(%s) %s ", (null == countField ? "*" : countField), alias);
        String upperSql = sql.toUpperCase();
        int start = upperSql.indexOf(" FROM ") + 1;
        int end = upperSql.lastIndexOf("ORDER BY ");
        if ( end == -1){
             end = upperSql.indexOf(" LIMIT ");
        }
        countSql += sql.substring(start, end == -1 ? sql.length() : end);

        return countSql;
    }

    /**
     * 移除sql的group部分
     *
     * @param sql 原始sql
     * @return 转化后的sql
     */
    public static String removeGROUP(final String sql) {

        String upperSql = sql.toUpperCase();
        int end = upperSql.indexOf(" GROUP ");
        return -1 == end ? sql : sql.substring(0, end);
    }

    /**
     * 移除sql的group部分
     *
     * @param sql 原始sql
     * @return 转化后的sql
     */
    public static String removePaging(final String sql) {

        String upperSql = sql.toUpperCase();
        int end = upperSql.indexOf(" LIMIT ");
        return -1 == end ? sql : sql.substring(0, end);
    }


    /**
     * 拼接分页部分
     *
     * @param pageNumber 页号
     * @param pageSize   每页大小
     * @return 拼装 分页sql
     */
    public static String forPaginate(int pageNumber, int pageSize) {
        int offset = pageSize * (pageNumber - 1);
        return String.format(" limit %s,%s", offset, pageSize);
    }


}
