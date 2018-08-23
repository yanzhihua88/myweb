package com.yzh.myweb.filter;

import org.springframework.security.core.AuthenticationException;

public class CodeAuthenticationException extends AuthenticationException {

    public CodeAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public CodeAuthenticationException(String msg) {
        super(msg);
    }
}
