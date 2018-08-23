package com.yzh.myweb.dto;

import java.io.Serializable;

public class BaseRequestDTO implements Serializable {
	private int page;
	private int size;
	public int getPage() {
		if(page==0){
			return 1;
		}
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		if(size==0){
			return 10;
		}
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
