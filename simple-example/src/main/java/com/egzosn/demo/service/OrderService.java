package com.egzosn.demo.service;

import com.bwton.lang.PageQuery;
import com.egzosn.demo.dao.OrderMapper;
import com.egzosn.demo.dao.UsersMapper;
import com.egzosn.demo.entity.Users;
import com.egzosn.demo.vo.UserVo;
import com.egzosn.mybatis.page.bean.Page;
import com.egzosn.mybatis.page.bean.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author egan
 * email egzosn@gmail.com
 * date 2018/5/10.22:35
 */
@Service
@Transactional(readOnly = true)
public class OrderService {


    @Autowired
    OrderMapper mapper;

    /**
     * 这里以分页入参方式进行查询所有
     *
     * @param paging 分页信息
     * @return 分页对象，但是这里返回所有的信息
     */
    public Page<Users> find(Paging paging) {

        return mapper.find(paging);
    }

    /**
     * 分页参数方式1
     *
     * @param cname  条件
     * @param paging 分页信息
     * @return 结果
     */
    public Page<Users> pages1(String cname, Paging paging) {
        return mapper.pages1(cname, paging);
    }

    /**
     * 分页参数方式2
     *
     * @param vo 入参
     * @return 结果
     */
    public Page<Users> pages2(UserVo vo) {
        return mapper.pages2(vo);
    }
}
