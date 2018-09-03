package com.egzosn.mybatis.page.bean;

import com.egzosn.mybatis.page.utils.SqlTools;

/**
 * 方言
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/9/3.14:59
 */
public enum Dialect {
    mysql {
        /**
         * 获取分页sql
         *
         * @param sql        原始sql
         * @param pageNumber 页号
         * @param pageSize   当前页展示数量
         * @return 分页sql
         */
        @Override
        public String forPaginate(String sql, int pageNumber, int pageSize) {
            return sql += SqlTools.mysqlForPaginate(pageNumber, pageSize);
        }

        /**
         * 获取统计的sql
         *
         * @param sql 原始sql
         * @return 方言对应的sql
         */
        @Override
        public String getCountSQL(String sql) {
            return SqlTools.getCountSQL(sql);
        }
    },oracle {
        /**
         * 获取分页sql
         *
         * @param sql        原始sql
         * @param pageNumber 页号
         * @param pageSize   当前页展示数量
         * @return 分页sql
         */
        @Override
        public String forPaginate(String sql, int pageNumber, int pageSize) {
            return SqlTools.oracleForPaginate(sql, pageNumber, pageSize);
        }

        /**
         * 获取统计的sql
         *
         * @param sql 原始sql
         * @return 方言对应的sql
         */
        @Override
        public String getCountSQL(String sql) {
            return SqlTools.getCountOracleSQL(sql);
        }
    };

    /**
     * 获取分页sql
     * @param sql 原始sql
     * @param pageNumber 页号
     * @param pageSize 当前页展示数量
     * @return  分页sql
     */
    public abstract String forPaginate(String sql, int pageNumber, int pageSize);

    /**
     * 获取统计的sql
     * @param sql 原始sql
     * @return 方言对应的sql
     */
    public abstract String  getCountSQL(final String sql);
}
