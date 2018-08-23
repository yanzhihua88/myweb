package com.yzh.myweb.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author generator
 * @since 2018-07-18
 */
@Data
public class CustomerQueryDTO extends BaseRequestDTO {

	private String customerId;
    /**
     * 企业名称
     */
	@JsonProperty("customer")
    private String customerName;
    /**
     * 客户等级 1潜在客户，2普通客户，3重点客户，4流失客户
     */
	@JsonProperty("level")
    private String customerLevel;
    /**
     * 意向业务  1 订单保理，2 应收保理，3 预付款
     */
	@JsonProperty("intentionBusiness")
    private String intentBusiness;
    /**
     * 数据来源 1 CRM，2 ABS，3 ERP，4 BGY
     */
	@JsonProperty("from")
    private String dataSource;

    //跟进开始时间
	@JsonProperty("startDate")
    private String startTime;
    //跟进结束时间
	@JsonProperty("endDate")
    private String endTime;
	
	private String userId;
	private String industryCate;
}
