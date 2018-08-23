package com.yzh.myweb.tokenstore;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.alibaba.fastjson.JSON;
import com.yzh.myweb.dto.UserDto;
import com.yzh.myweb.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomTokenStoreDelegator implements TokenStore {

    private TokenStore delegate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    public CustomTokenStoreDelegator(TokenStore delegate) {
		this.delegate = delegate;
	}

	@Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication authentication = delegate.readAuthentication(token);
        return authentication;
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return delegate.readAuthentication(token);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        delegate.storeAccessToken(token, authentication);
        UserDto user = userService.findByUserLoginName(authentication.getName());
        if(user != null){
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps("USER_INFO:"+token.getValue());
            boundHashOperations.putIfAbsent(token.getValue(), JSON.toJSONString(user));
            boundHashOperations.expire(token.getExpiresIn(), TimeUnit.SECONDS);
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return delegate.readAccessToken(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        delegate.removeAccessToken(token);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        delegate.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return delegate.readRefreshToken(tokenValue);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return delegate.readAuthenticationForRefreshToken(token);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        delegate.removeRefreshToken(token);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        delegate.removeAccessTokenUsingRefreshToken(refreshToken);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return delegate.getAccessToken(authentication);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return delegate.findTokensByClientIdAndUserName(clientId, userName);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return delegate.findTokensByClientId(clientId);
    }

}
