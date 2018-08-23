package com.yzh.myweb.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yzh.myweb.enums.CustomerLevel;
import com.yzh.myweb.enums.Sex;

import lombok.Data;

/**
 * @author generator
 * @since 2018-07-18
 */
@Data
public class CustomerResponseDTO{

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @JsonProperty("customerId")
    private Integer customerId;
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
    public String getCustomerLevel() {
    	if(StringUtils.isEmpty(CustomerLevel.getValueByCode(this.customerLevel))){
    		return this.customerLevel;
    	}
		return CustomerLevel.getValueByCode(this.customerLevel);
	}
    
    /**
     * 企业英文名称
     */
    @JsonProperty("eName")
    private String englishName;
    /**
     * 企业证件类型
     */
    @JsonProperty("customerDocumentType")
    private String licenseType;
    /**
     * 企业证件号码
     */
    @JsonProperty("customerDocumentNumber")
    private String licenseCode;
    /**
     * 企业证件有效期
     */
    @JsonProperty("customerDocumentValidity")
    private String licenseTerm;
    /**
     * 企业类型
     */
    @JsonProperty("enterpriseType")
    private String enterpriseType;
    /**
     * 经营状态：开业/存续/停业/清算
     */
    @JsonProperty("operatingStatus")
    private String operatingStatus;
    /**
     * 注册时间
     */
    @JsonProperty("establishmentDate")
    private String regDate;
    /**
     * 注册地址
     */
    @JsonProperty("regAddress")
    private String regAddress;
    /**
     * 营业期限
     */
    @JsonProperty("businessTerm")
    private String businessTerm;
    /**
     * 登记机关
     */
    @JsonProperty("registrationAuthority")
    private String registrationAuthority;
    /**
     * 行业分类(1建筑建材,2医药卫生,3石油化工,4其他)
     */
    @JsonProperty("industryClassification")
    private String industryCate;

    /**
     * 核准日期
     */
    @JsonProperty("approvalDate")
    private String approvalDate;
    /**
     * 住所
     */
    @JsonProperty("residence")
    private String residentialAddress;
    /**
     * 经营地址（企业地址）
     */
    @JsonProperty("address")
    private String businessAddress;
    /**
     * 主营产品（经营范围）
     */
    @JsonProperty("businessScope")
    private String product;
    /**
     * 法人姓名
     */
    @JsonProperty("legalPersonName")
    private String lawPerson;
    /**
     * 法人手机
     */
    @JsonProperty("legalPersonMobile")
    private String lawMobile;
    /**
     * 法人性别(1男,2女)
     */
    @JsonProperty("legalPersonSex")
    private String lawSex;
    public String getLawSex() {
    	if(StringUtils.isEmpty(Sex.getValueByCode(this.lawSex))){
    		return this.lawSex;
    	}
		return Sex.getValueByCode(this.lawSex);
	}
    /**
     * 法人证件类型
     */
    @JsonProperty("legalPersonDocumentType")
    private String lawCertType;
    /**
     * 法人证件号码
     */
    @JsonProperty("legalPersonDocumentNumber")
    private String lawCertCode;
    /**
     * 法人证件有效期
     */
    @JsonProperty("legalPersonDocumentValidity")
    private String lawCertTerm;
    /**
     * 法人国籍
     */
    @JsonProperty("legalPersonNationality")
    private String lawNationality;
    /**
     * 法人反欺诈结果
     */
    @JsonProperty("lawAntifraudResult")
    private String lawAntifraudResult;
    /**
     * 联系人姓名
     */
    @JsonProperty("contactName")
    private String contactName;
    /**
     * 联系人性别
     */
    @JsonProperty("contactSex")
    private String contactSex;
    public String getContactSex() {
    	if(StringUtils.isEmpty(Sex.getValueByCode(this.contactSex))){
    		return this.contactSex;
    	}
		return Sex.getValueByCode(this.contactSex);
	}
    /**
     * 联系人手机
     */
    @JsonProperty("contactMobile")
    private String contactMobile;
    /**
     * 联系人办公电话
     */
    @JsonProperty("contactTel")
    private String contactPhone;
    /**
     * 联系人邮箱
     */
    @JsonProperty("contactEmail")
    private String contactEmail;
    /**
     * 联系人证件类型
     */
    @JsonProperty("contactDocumentType")
    private String contactCredType;
    /**
     * 联系人证件号码
     */
    @JsonProperty("contactDocumentNumber")
    private String contactCredCode;
    /**
     * 联系人证件有效期
     */
    @JsonProperty("contactDocumentValidity")
    private String contactCertTerm;
    /**
     * 联系人反欺诈结果
     */
    @JsonProperty("contactAntifraudResult")
    private String contactAntifraudResult;
    /**
     * 控制人姓名
     */
    @JsonProperty("controlPeopleName")
    private String controllerName;
    /**
     * 控制人性别
     */
    @JsonProperty("controlPeopleSex")
    private String controllerSex;
    public String getControllerSex() {
    	if(StringUtils.isEmpty(Sex.getValueByCode(this.controllerSex))){
    		return this.controllerSex;
    	}
		return Sex.getValueByCode(this.controllerSex);
	}
    /**
     * 控制人手机
     */
    @JsonProperty("controlPeopleMobile")
    private String controllerMobile;
    /**
     * 控制人办公电话
     */
    @JsonProperty("controlPeopleTel")
    private String controllerPhone;
    /**
     * 控制人邮箱
     */
    @JsonProperty("controlPeopleEmail")
    private String controllerEmail;
    /**
     * 控制人证件类型
     */
    @JsonProperty("controlPeopleDocumentType")
    private String controllerCredType;
    /**
     * 控制人证件号码
     */
    @JsonProperty("controlPeopleDocumentNumber")
    private String controllerCredCode;
    /**
     * 控制人证件有效期
     */
    @JsonProperty("controlPeopleDocumentValidity")
    private String controllerCertTerm;
    /**
     * 控制人反欺诈结果
     */
    @JsonProperty("controllerAntifraudResult")
    private String controllerAntifraudResult;
    /**
     * 对应核心企业
     */
    @JsonProperty("coreEnterprise")
    private String coreEnterprise;
    /**
     * 意向业务
     */
    @JsonProperty("intentionBusiness")
    private String intentBusiness;

    /**
     * 所属城市
     */
    @JsonProperty("belongCity")
    private String belongCity;
    /**
     * 数据来源 1 CRM，2 ABS，3 ERP，4 BGY
     */
    @JsonProperty("from")
    private String dataSource;
    /**
     * 数据来源ID
     */
    @JsonProperty("relatedSourceId")
    private String relatedSourceId;
    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;
    /**
     * 最后跟进时间
     */
    @JsonProperty("lastUpdateTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastFollowDate;
    /**
     * 是否删除(0:未删除,1:删除)
     */
    private String isDeleted;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


}
