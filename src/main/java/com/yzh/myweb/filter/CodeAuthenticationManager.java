package com.yzh.myweb.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * 由于是拓展spring security的验证，不能在ProviderManager中调用到，这里单独处理一个验证管理器。
 */
public class CodeAuthenticationManager implements AuthenticationManager {

    /**
     * 自己实现的验证码认证器
     */
    private AuthenticationProvider provider;

    public CodeAuthenticationManager(AuthenticationProvider provider) {
        Assert.notNull(provider, "provider cannot be null");
        this.provider = provider;
    }

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        return this.provider.authenticate(authentication);
    }
}
