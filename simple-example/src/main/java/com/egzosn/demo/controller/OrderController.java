package com.egzosn.demo.controller;

import com.bwton.lang.PageQuery;
import com.egzosn.demo.entity.Users;
import com.egzosn.demo.service.OrderService;
import com.egzosn.demo.vo.UserVo;
import com.egzosn.mybatis.page.bean.Page;
import com.egzosn.mybatis.page.bean.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/10.22:34
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService service;

    /**
     * 这里以分页入参方式进行查询所有，包装成分页对象返回所有
     * @return 分页对象，但是这里返回所有的信息
     */
    @RequestMapping("find")
    public Page<Users> find(){
        Paging paging = new Paging();
        //这里关闭分页查询
        //paging.setPaging(false);
        return  service.find(paging);
    }
    /**
     * 分页参数方式1
     * @param cname 条件
     * @param paging 分页信息
     * @return 结果
     */
    @RequestMapping("pages1")
    public Page<Users> pages1(String cname, Paging paging){
        service.pages1(cname, paging);
        return service.pages1(cname, paging);
    }

    /**
     * 分页参数方式2
     * @param vo 入参
     * @return 结果
     */
    @RequestMapping("pages2")
    public Page<Users> pages2(UserVo vo){
        return service.pages2(vo);
    }
}
