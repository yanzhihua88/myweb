package com.yzh.myweb.enums;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public enum IntentBusinessType {
	//意向业务  1 订单保理，2 应收保理，3 预付款
	//ERP : 1.订单融资 2.发票融资 3.池融资
	ORDER("1","订单保理","订单融资"),
	RECEIVABLE("2","应收保理","发票融资"),
	ADVANCE("3","预付款","预付款"),
	POOL("4","池融资","池融资");
	
	private String code;
	private String value;
	private String erpValue;
	
	private IntentBusinessType(String code, String value,String erpValue) {
		this.setCode(code);
		this.setValue(value);
		this.setErpValue(erpValue);
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		for(IntentBusinessType t : values()){
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
		for(IntentBusinessType t : values()){
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
		for(IntentBusinessType t : values()){
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
