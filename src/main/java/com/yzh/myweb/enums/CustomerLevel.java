package com.yzh.myweb.enums;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public enum CustomerLevel {
	//客户等级  1潜在客户，2普通客户，3重点客户，4流失客户
	LATENT("1","潜在客户"),
	COMMON("2","普通客户"),
	IMPORTANT("3","重点客户"),
	FLOWAWAY("4","流失客户");
	
	private String code;
	private String value;
	
	private CustomerLevel(String code, String value) {
		this.setCode(code);
		this.setValue(value);
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		for(CustomerLevel t : values()){
			if(t.getCode().equals(code)){
				return t.getValue();
			}
		}
		return "";
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
