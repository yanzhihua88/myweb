package com.yzh.myweb.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yzh.myweb.config.DbContextHolder;
import com.yzh.myweb.enums.DataSourceType;
import com.yzh.myweb.mapper.BgyDataMapper;

import lombok.extern.slf4j.Slf4j;


@Component
@Aspect
@Order(-100) //这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
@Slf4j
public class DataSourceSwitchAdvice {
    @Pointcut("execution(* com.yzh.myweb.mapper.*.*(..))")
    private void joinPoint() {
    }

    @Before("joinPoint()")
     public void setDataSourceKey(JoinPoint point) {
        if (point.getTarget() instanceof BgyDataMapper) {
            log.info("切换到BGY datasource");
            DbContextHolder.setDbType(DataSourceType.A);
        } else {
            log.info("切换到CRM datasource");
            DbContextHolder.setDbType(DataSourceType.B);
        }
    }

    @AfterReturning("joinPoint()")
    public void resetKaHolder(JoinPoint joinPoint) {
        log.debug("----------------清除数据库连接----------------");
        DbContextHolder.clearDbType();
    }
}
