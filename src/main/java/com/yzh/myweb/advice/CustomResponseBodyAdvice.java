package com.yzh.myweb.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.yzh.myweb.common.CustomResponseBody;
import com.yzh.myweb.common.PageResult;

@RestControllerAdvice("com.yzh.myweb.controller")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // already processed by ExceptionHandler
        return !"getTransferAttachment".equals(returnType.getMethod().getName())
                    && returnType.getContainingClass() != CustomExceptionAdvice.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        CustomResponseBody<Object> responseBody = new CustomResponseBody<>("200", "成功");

        if (body == null) {
            // when return type of controller method is void
        } else if (body instanceof PageResult) {
            responseBody.setPageResult((PageResult) body);
        } else {
            responseBody.setData(body);
        }

        return responseBody;
    }

}
