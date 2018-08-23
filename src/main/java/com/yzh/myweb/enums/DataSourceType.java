package com.yzh.myweb.enums;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public enum DataSourceType {
	A("1","db1"),
	B("2","db2"),
	C("3","db3"),
	D("4","db4");
	
	private String code;
	private String value;
	
	private DataSourceType(String code, String value) {
		this.setCode(code);
		this.setValue(value);
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		for(DataSourceType t : values()){
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
