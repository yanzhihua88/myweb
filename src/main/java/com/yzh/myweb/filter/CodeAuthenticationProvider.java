package com.yzh.myweb.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yzh.myweb.dto.UserDto;
import com.yzh.myweb.exception.CommonException;
import com.yzh.myweb.service.UserService;

public class CodeAuthenticationProvider implements AuthenticationProvider {

    private RedisTemplate redisTemplate;

    private UserService userService;
    
    public CodeAuthenticationProvider(RedisTemplate redisTemplate, UserService userService){
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CodeAuthenticationToken custCaptchaToken = (CodeAuthenticationToken) authentication;
        String captcha = custCaptchaToken.getCaptcha().toString();
        String captchaKey = custCaptchaToken.getCaptchaKey().toString();
        String userName = custCaptchaToken.getName().toString();
        String password = custCaptchaToken.getPwd().toString();

        if(StringUtils.isBlank(captcha) || StringUtils.isBlank(captchaKey)){
            throw new CommonException("验证码不能为空");
        }
        Object code = redisTemplate.boundValueOps("captchaKey:"+captchaKey).get();

        if(code == null){
            throw new CommonException("验证码已过期");
        }
        if(!captcha.equals(code)){
            throw new CommonException("验证码错误");
        }
        if(StringUtils.isBlank(userName)){
            throw new CommonException("用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new CommonException("密码不能为空");
        }
        UserDto user = userService.findByUserLoginName(userName);
        //禁止运营端用户登录企业端平台 ||  禁止企业用户登录运营端平台
        if(user == null){
            throw new CommonException("用户在系统中不存在");
        }
        try {
            boolean  matches = new BCryptPasswordEncoder().matches(password,user.getPwd());
            if(!matches){
                throw new CommonException("账户或密码错误");
            }
        }catch (Exception e){
            throw new CommonException("账户或密码错误");
        }
        //返回验证成功对象
        custCaptchaToken.setFlag(true);
        return custCaptchaToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (CodeAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
