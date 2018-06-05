# mybatis-page-plug
超级精简，超级简单，超级优雅实现mybatis分页功能


# 简单使用
### 将 com.egzosn.mybatis.page.plug.PagingInterceptor 拦截器加入至spring 容器即可

#### 本项目简单例子是spring boot 项目，直接运行 com.egzosn.demo.DemoApplication 即可

这里对 com.egzosn.demo.dao.UsersMapper 三种运用场景


 
入参  `com.egzosn.demo.controller.UsersController.pages1`

http://127.0.0.1:8080/pages1?cname=egan&page=1&rows=5



对象继承  `com.egzosn.demo.service.UsersService.pages2`

http://127.0.0.1:8080/pages2?cname=egan&page=1&rows=5



这里以分页入参方式进行查询所有，包装成分页对象返回所有 `com.egzosn.demo.controller.UsersController.find`

http://127.0.0.1:8080/find