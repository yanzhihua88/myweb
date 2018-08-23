package com.yzh.myweb.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 用户名密码合法验证类 MailAuthenticator.java
 * 
 * @author mark.huang
 * @create 2017年9月4日 下午1:24:49
 */
public class MailAuthenticator extends Authenticator {
	private String username;
	private String password;

	public MailAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	String getUsername() {
		return username;
	}
}
