package com.egzosn.demo.vo;


import com.bwton.lang.PageQuery;

/**
 * 通过继承的方式来做分页的入参
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/6/5.21:13
 */
public class UserVo extends PageQuery {
    /**
     * 条件
     */
    private String cname;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
