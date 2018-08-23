package com.yzh.myweb.dto;

import lombok.Data;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = -2784421765833160110L;

	private int id;

	@NotBlank(message = "用户名不能为空!")
	private String name;

	@NotBlank(message = "密码不能为空!")
	private String pwd;

	@NotBlank(message = "captcha不能为空!")
	private String captcha;

	@NotBlank(message = "captchaKey不能为空!")
	private String captchaKey;

}
