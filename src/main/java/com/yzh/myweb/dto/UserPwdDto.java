package com.yzh.myweb.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserPwdDto implements Serializable{
	
	private static final long serialVersionUID = 3903904518490713351L;
	
	@NotBlank(message="用户名不能为空!")
    private String name;

	@NotBlank(message="旧密码不能为空!")
    private String pwd;
	
	@NotBlank(message="新密码不能为空!")
	private String newPwd;

	
}