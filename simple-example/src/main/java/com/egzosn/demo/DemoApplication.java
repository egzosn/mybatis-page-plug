package com.egzosn.demo;

import com.egzosn.mybatis.page.plug.PagingInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan(
		basePackages = {"com.**.dao", "com.**.mybatis.mapper"}
)
//这里引入分页拦截器
@Import(PagingInterceptor.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
