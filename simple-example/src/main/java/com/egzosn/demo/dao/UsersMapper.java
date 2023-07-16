package com.egzosn.demo.dao;

import com.bwton.lang.Page;
import com.bwton.lang.PageQuery;
import com.egzosn.demo.vo.UserVo;
import com.egzosn.demo.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *  契约
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/9.9:42
 */
@Repository
public interface UsersMapper {
     /**
      * 这里以分页入参方式进行查询所有
      * @param paging 分页信息
      * @return 分页对象，但是这里返回所有的信息
      */
     Page<Users> find(PageQuery paging);

     /**
      * 分页参数方式1
      * @param cname 条件
      * @param paging 分页信息
      * @return 结果
      */
     com.bwton.lang.Page<Users> pages1(UserVo userVo);
     /**
      * 分页参数方式2
      * @param vo 入参
      * @return 结果
      */
     Page<Users> pages2(UserVo vo);
}
