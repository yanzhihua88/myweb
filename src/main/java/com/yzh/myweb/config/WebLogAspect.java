package com.yzh.myweb.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yzh.myweb.common.CustomUser;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();
    
    @Autowired
    protected HttpServletRequest request;
    
//    @Autowired
//    private IUserOperationService userOperationService;

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * com.shengyecapital.crm.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        log.info("REQUEST : " + request.getRequestURI());
        log.info("PARAM : " + joinPoint.getArgs()[0]);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    private CustomUser getCurrentUser(){
        String  auth = request.getHeader("Authorization");
        if(StringUtils.isBlank(auth)){
            return null;
        }
        String token = auth.split(" ")[1];
        if(StringUtils.isBlank(token)){
            return null;
        }
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps("USER_INFO:"+token);
        Object object = boundHashOperations.get(token);
        CustomUser user = null;
        if(object != null){
            user = JSON.parseObject(object.toString(), CustomUser.class);
        }
        return user;
    }

}