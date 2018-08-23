package com.yzh.myweb.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CodeAuthenticationToken extends AbstractAuthenticationToken {

    private String name;

    private String pwd;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码key
     */
    private String captchaKey;

    /**
     * 验证码验证标识
     * true:通过
     * false:错误
     */
    private boolean flag;

    public CodeAuthenticationToken() {
        super(null);
    }

    public CodeAuthenticationToken(String captcha, String captchaKey, String username, String password) {
        super(null);
        this.captcha = captcha;
        this.captchaKey = captchaKey;
        this.name = username;
        this.pwd = password;
        this.flag = false;
        setAuthenticated(false);
    }

    public CodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String captcha) {
        super(authorities);
        this.captcha = captcha;
        this.flag = false;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public Object getCaptcha() {
        return captcha;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getCaptchaKey() {
        return captchaKey;
    }
    
    

}