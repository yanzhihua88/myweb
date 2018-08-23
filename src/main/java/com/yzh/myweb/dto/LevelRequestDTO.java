package com.yzh.myweb.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class LevelRequestDTO{
	
	@NotEmpty(message="客户等级不能为空")
    private String level; //客户等级
	
	@NotEmpty(message="企业id不能为空")
    private List<Integer> customerIds;//企业id
	
	private String remark;//流失原因
}
