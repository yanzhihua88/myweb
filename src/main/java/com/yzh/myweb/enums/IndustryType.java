package com.yzh.myweb.enums;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public enum IndustryType {
	//行业分类 :(1基建工程,2医药医疗,3能源化工,4其他,5医疗器械)
	//ERP ：1、医药医疗  2、基建工程 3、能源化工 4、其他
	JIANZHU("1","基建工程","建筑建材"),
	YIYAO("2","医药医疗","医药卫生"),
	SHIYOU("3","能源化工","石油化工"),
	OTHER("4","其他","其他"),
	QIXIE("5", "医疗器械","医疗器械");
	
	private String code;
	private String value;
	private String erpValue;
	
	private IndustryType(String code, String value, String erpValue) {
		this.setCode(code);
		this.setValue(value);
		this.setErpValue(erpValue);
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		for(IndustryType t : values()){
			if(t.getCode().equals(code)){
				return t.getValue();
			}
		}
		return "";
	}
	
	public static String getCodeByValue(String value){
		if(StringUtils.isEmpty(value)){
			return "";
		}
		for(IndustryType t : values()){
			if(t.getValue().equals(value)){
				return t.getCode();
			}
		}
		return "";
	}
	
	public static String getCodeByErpValue(String erpValue){
		if(StringUtils.isEmpty(erpValue)){
			return "";
		}
		for(IndustryType t : values()){
			if(t.getErpValue().equals(erpValue)){
				return t.getCode();
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

	public String getErpValue() {
		return erpValue;
	}

	public void setErpValue(String erpValue) {
		this.erpValue = erpValue;
	}
	
}
