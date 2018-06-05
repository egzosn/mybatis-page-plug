package com.egzosn.mybatis.page.plug;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class PagingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor){
            return Plugin.wrap(new PageExecutor((Executor)target), this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
//        String dialect = properties.getProperty("dialect");
//        System.out.println("mybatis intercept dialect:"+ dialect);
    }
}
