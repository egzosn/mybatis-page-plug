package com.egzosn.mybatis.page.bean;

/**
 * 分页入参使用
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/16.14:34
 */
public class Paging {

    /**
     * 当前页
     */
    protected Integer page = 1;

    /**
     * 每页记录数
     */
    protected Integer rows = 10;
    /**
     * 是否分页
     */
    protected  boolean paging = true;
    /**
     * 统计是否移除select与order by 部分
     */
    private boolean removeSelect = true;

    /**
     * 设置当前页
     * @param page 当前页
     */
    public void setPage(Integer page) {
        if (null == page || page <= 0) {
            return;
        }
        this.page = page;
    }

    /**
     * 获取当前页
     * @return 当前页
     */
    public Integer getPage() {
        return page;
    }



    /**
     * 获取每页记录数
     * @return 每页记录数
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * 设置每页记录数
     * @param rows 每页记录数
     */
    public void setRows(Integer rows) {
        if (null == rows) {
            return;
        }
        this.rows = rows;
    }

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public boolean isRemoveSelect() {
        return removeSelect;
    }

    public void setRemoveSelect(boolean removeSelect) {
        this.removeSelect = removeSelect;
    }

}
