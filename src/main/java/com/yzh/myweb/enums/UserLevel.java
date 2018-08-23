package com.yzh.myweb.enums;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public enum UserLevel {
	//用户级别 1Boss 2主办 3协办
	BOSS("1","Boss"),
	LEADER("2","主办"),
	FOLLOW("3","协办");
	
	private String code;
	private String value;
	
	private UserLevel(String code, String value) {
		this.setCode(code);
		this.setValue(value);
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		for(UserLevel t : values()){
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
