package com.yzh.myweb.enums;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public enum Sex {
	//性别(1男,2女)
	MALE("1","男"),
	FEMALE("2","女");
	
	private String code;
	private String value;
	
	private Sex(String code, String value) {
		this.setCode(code);
		this.setValue(value);
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		for(Sex t : values()){
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
