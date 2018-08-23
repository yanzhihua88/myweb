package com.yzh.myweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yzh.myweb.dto.UserDto;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;

    public UserDto getCurrentUser(){
        String  auth = request.getHeader("Authorization");
        if(StringUtils.isEmpty(auth)){
            return null;
        }
        String token = auth.split(" ")[1];
        if(StringUtils.isEmpty(token)){
            return null;
        }
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps("USER_INFO:"+token);
        Object object = boundHashOperations.get(token);
        UserDto user = null;
        if(object != null){
            user = JSON.parseObject(object.toString(), UserDto.class);
        }
        return user;
    }

    public int getCurrentUserId(){
        return this.getCurrentUser() != null ? this.getCurrentUser().getId() : 0;
    }
}
