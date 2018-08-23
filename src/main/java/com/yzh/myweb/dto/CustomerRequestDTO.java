package com.yzh.myweb.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author generator
 * @since 2018-07-18
 */
@Data
public class CustomerRequestDTO implements Serializable {

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
    @NotEmpty(message="企业名称不能为空")
    private String customerName;
    /**
     * 客户等级 1潜在客户，2普通客户，3重点客户，4流失客户
     */
    @JsonProperty("level")
    private String customerLevel;
    /**
     * 企业英文名称
     */
    @JsonProperty("eName")
    private String englishName;
    /**
     * 企业证件类型
     */
    @JsonProperty("customerDocumentType")
    @NotEmpty(message="企业证件类型不能为空")
    private String licenseType;
    /**
     * 企业证件号码
     */
    @JsonProperty("customerDocumentNumber")
    @NotEmpty(message="企业证件号码不能为空")
    private String licenseCode;
    /**
     * 企业证件有效期
     */
    @JsonProperty("customerDocumentValidity")
    @NotEmpty(message="企业证件有效期不能为空")
    private String licenseTerm;
    /**
     * 企业类型
     */
    @JsonProperty("enterpriseType")
    @NotEmpty(message="企业类型不能为空")
    private String enterpriseType;
    /**
     * 经营状态：开业/存续/停业/清算
     */
    @JsonProperty("operatingStatus")
    @NotEmpty(message="经营状态不能为空")
    private String operatingStatus;
    /**
     * 注册时间
     */
    @JsonProperty("establishmentDate")
    @NotEmpty(message="注册时间不能为空")
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
    @NotEmpty(message="营业期限不能为空")
    private String businessTerm;
    /**
     * 登记机关
     */
    @JsonProperty("registrationAuthority")
    @NotEmpty(message="登记机关不能为空")
    private String registrationAuthority;
    /**
     * 行业分类(1建筑建材,2医药卫生,3石油化工,4其他)
     */
    @JsonProperty("industryClassification")
    @NotEmpty(message="行业分类不能为空")
    private String industryCate;
    /**
     * 核准日期
     */
    @JsonProperty("approvalDate")
    @NotEmpty(message="核准日期不能为空")
    private String approvalDate;
    /**
     * 住所
     */
    @JsonProperty("residence")
    //@NotEmpty(message="住所不能为空")
    private String residentialAddress;
    /**
     * 经营地址（企业地址）
     */
    @JsonProperty("address")
    @NotEmpty(message="企业地址不能为空")
    private String businessAddress;
    /**
     * 主营产品（经营范围）
     */
    @JsonProperty("businessScope")
    @NotEmpty(message="主营产品（经营范围）不能为空")
    private String product;
    /**
     * 法人姓名
     */
    @JsonProperty("legalPersonName")
    @NotEmpty(message="法人姓名不能为空")
    private String lawPerson;
    
    /**
     * 法人手机
     */
    @JsonProperty("legalPersonMobile")
    @NotEmpty(message="法人手机不能为空")
    private String lawMobile;
    /**
     * 法人性别(1男,2女)
     */
    @JsonProperty("legalPersonSex")
    @NotEmpty(message="法人性别(1男,2女)不能为空")
    private String lawSex;
    /**
     * 法人证件类型
     */
    @JsonProperty("legalPersonDocumentType")
    @NotEmpty(message="法人证件类型不能为空")
    private String lawCertType;
    /**
     * 法人证件号码
     */
    @JsonProperty("legalPersonDocumentNumber")
    @NotEmpty(message="法人证件号码不能为空")
    private String lawCertCode;
    /**
     * 法人证件有效期
     */
    @JsonProperty("legalPersonDocumentValidity")
    @NotEmpty(message="法人证件有效期不能为空")
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
    @NotEmpty(message="联系人姓名不能为空")
    private String contactName;
    /**
     * 联系人性别
     */
    @JsonProperty("contactSex")
    @NotEmpty(message="联系人性别不能为空")
    private String contactSex;
    /**
     * 联系人手机
     */
    @JsonProperty("contactMobile")
    @NotEmpty(message="联系人手机不能为空")
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
    @NotEmpty(message="联系人证件类型不能为空")
    private String contactCredType;
    /**
     * 联系人证件号码
     */
    @JsonProperty("contactDocumentNumber")
    @NotEmpty(message="联系人证件号码不能为空")
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
    @NotEmpty(message="控制人姓名不能为空")
    private String controllerName;
    /**
     * 控制人性别
     */
    @JsonProperty("controlPeopleSex")
    @NotEmpty(message=" 控制人性别不能为空")
    private String controllerSex;
    /**
     * 控制人手机
     */
    @JsonProperty("controlPeopleMobile")
    @NotEmpty(message="控制人手机不能为空")
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
    @JsonProperty("lastFollowDate")
    private Date lastFollowDate;

}
