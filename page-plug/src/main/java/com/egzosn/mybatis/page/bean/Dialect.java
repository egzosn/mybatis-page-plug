package com.egzosn.mybatis.page.bean;


import com.egzosn.mybatis.page.utils.SQLTools;

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
            return sql + SQLTools.forPaginate(pageNumber, pageSize);
        }

        /**
         * 获取统计的sql
         *
         * @param sql 原始sql
         * @return 方言对应的sql
         */
        @Override
        public String getCountSQL(String sql) {
            return SQLTools.getCountSQL(sql);
        }
    }, oracle {
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
            return SQLTools.forPaginate(sql, pageNumber, pageSize);
        }

        /**
         * 获取统计的sql
         *
         * @param sql 原始sql
         * @return 方言对应的sql
         */
        @Override
        public String getCountSQL(String sql) {
            return SQLTools.getCountOracleSQL(sql);
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
    /**
     * 获取统计的sql
     * @param removeSelect 是否移除select与order by 部分
     */
    public  String  getCountPrefixSQL( boolean removeSelect){
        return " SELECT  COUNT(0) FROM ( ";
    }
    /**
     * 获取统计的sql
     * @param removeSelect 是否移除select与order by 部分
     */
    public  String  getCountSuffixSQL( boolean removeSelect){
        return " )";
    }
    /**
     * 获取统计的sql
     *
     * @param sql 原始sql
     * @return 方言对应的sql
     * @param removeSelect 是否移除select与order by 部分
     */
    public String getCountSQL(final String sql, boolean removeSelect) {
        if (removeSelect) {
            return getCountSQL(sql);
        }
        return SQLTools.getCount(sql, "0");
    }
}
