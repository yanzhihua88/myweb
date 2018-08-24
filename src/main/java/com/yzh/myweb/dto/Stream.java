package com.yzh.myweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yzh.myweb.annotation.Email;
import com.yzh.myweb.annotation.FiledName;
import com.yzh.myweb.annotation.IsEmpty;
import com.yzh.myweb.annotation.MaxSize;
import com.yzh.myweb.annotation.MinSize;
import com.yzh.myweb.annotation.Mobile;
import com.yzh.myweb.annotation.PersonName;

import lombok.Data;

@Data
public class Stream {

	@JsonProperty("id")
	@FiledName(name="企业id")
	private String id;
	
	@JsonProperty("enterprise")
	@IsEmpty()
	@MinSize(min=3)
	@MaxSize(max=30)
	@FiledName(name="企业名称")
	private String enterprise;
	
	@JsonProperty("contact")
	@IsEmpty()
	@MinSize(min=2)
	@MaxSize(max=30)
	@FiledName(name="联系人姓名")
	@PersonName()
	private String contact;
	
	@JsonProperty("phone")
	@IsEmpty()
	@FiledName(name="联系人电话")
	@Mobile()
	private String phone;
	
	@JsonProperty("email")
	@IsEmpty()
	@FiledName(name="联系人邮箱")
	@Email()
	private String email;
	
	@JsonProperty("address")
	@IsEmpty()
	@MinSize(min=3)
	@MaxSize(max=30)
	@FiledName(name="企业地址")
	private String address;
}
