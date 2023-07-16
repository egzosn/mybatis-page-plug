package com.egzosn.demo.controller;

import com.bwton.lang.Page;
import com.bwton.lang.PageQuery;
import com.egzosn.demo.entity.Users;
import com.egzosn.demo.service.UsersService;
import com.egzosn.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/10.22:34
 */
@RestController
public class UsersController {
    @Autowired
    UsersService service;

    /**
     * 这里以分页入参方式进行查询所有，包装成分页对象返回所有
     * @return 分页对象，但是这里返回所有的信息
     */
    @RequestMapping("find")
    public Page<Users> find(){
        PageQuery paging = new PageQuery();
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
    public Page<Users> pages1(String cname, PageQuery paging){
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
