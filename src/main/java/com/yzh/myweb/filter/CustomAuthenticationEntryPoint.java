package com.yzh.myweb.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzh.myweb.common.CustomResponseBody;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     */
    public static final String OAUTH_ERROR_MESSAGE = "鉴权失败";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        CustomResponseBody responseBody = new CustomResponseBody("110", OAUTH_ERROR_MESSAGE);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    /**
     * 转义OAuth抛出的错
     * @param e
     * @return
     */
    /*
    private String translateExceptionMsg(Throwable e){
        if(e instanceof InvalidClientException){
            return "错误的client_id";
        }else if(e instanceof UnauthorizedClientException){
            return "client没有授权";
        }else if(e instanceof InvalidGrantException){
            return "不支持的grant_type";
        }else if(e instanceof InvalidScopeException){
            return "错误的scope";
        }else if(e instanceof InvalidTokenException){
            return "错误的token";
        }else if(e instanceof InvalidRequestException){
            return "请求有误";
        }else if(e instanceof RedirectMismatchException){
            return "跳转url不存在";
        }else if(e instanceof UnsupportedGrantTypeException){
            return "不支持该grant_type";
        }else if(e instanceof UnsupportedResponseTypeException){
            return "不支持的返回类型";
        }else if(e instanceof UserDeniedAuthorizationException){
            return "权限不足,拒绝访问";
        }
        return "OAuth验证失败";
    }*/
}
