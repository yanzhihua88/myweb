package com.yzh.myweb.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户详情表
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
@Data
@Accessors(chain = true)
@TableName("t_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Integer customerId;
    /**
     * 企业名称
     */
    private String customerName;
    /**
     * 客户等级 1潜在客户，2普通客户，3重点客户，4流失客户
     */
    private String customerLevel;
    /**
     * 企业英文名称
     */
    private String englishName;
    /**
     * 企业证件类型
     */
    private String licenseType;
    /**
     * 企业证件号码
     */
    private String licenseCode;
    /**
     * 企业证件有效期
     */
    private String licenseTerm;
    /**
     * 企业类型
     */
    private String enterpriseType;
    /**
     * 经营状态：开业/存续/停业/清算
     */
    private String operatingStatus;
    /**
     * 注册时间
     */
    private String regDate;
    /**
     * 注册地址
     */
    private String regAddress;
    /**
     * 营业期限
     */
    private String businessTerm;
    /**
     * 登记机关
     */
    private String registrationAuthority;
    /**
     * 行业分类(1基建工程,2医药医疗,3能源化工,4其他)
     */
    private String industryCate;
    /**
     * 核准日期
     */
    private String approvalDate;
    /**
     * 住所
     */
    private String residentialAddress;
    /**
     * 经营地址（企业地址）
     */
    private String businessAddress;
    /**
     * 主营产品（经营范围）
     */
    private String product;
    /**
     * 法人姓名
     */
    private String lawPerson;
    /**
     * 法人手机
     */
    private String lawMobile;
    /**
     * 法人性别(1男,2女)
     */
    private String lawSex;
    /**
     * 法人证件类型
     */
    private String lawCertType;
    /**
     * 法人证件号码
     */
    private String lawCertCode;
    /**
     * 法人证件有效期
     */
    private String lawCertTerm;
    /**
     * 法人国籍
     */
    private String lawNationality;
    /**
     * 法人反欺诈结果
     */
    private String lawAntifraudResult;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人性别
     */
    private String contactSex;
    /**
     * 联系人手机
     */
    private String contactMobile;
    /**
     * 联系人办公电话
     */
    private String contactPhone;
    /**
     * 联系人邮箱
     */
    private String contactEmail;
    /**
     * 联系人证件类型
     */
    private String contactCredType;
    /**
     * 联系人证件号码
     */
    private String contactCredCode;
    /**
     * 联系人证件有效期
     */
    private String contactCertTerm;
    /**
     * 联系人反欺诈结果
     */
    private String contactAntifraudResult;
    /**
     * 控制人姓名
     */
    private String controllerName;
    /**
     * 控制人性别
     */
    private String controllerSex;
    /**
     * 控制人手机
     */
    private String controllerMobile;
    /**
     * 控制人办公电话
     */
    private String controllerPhone;
    /**
     * 控制人邮箱
     */
    private String controllerEmail;
    /**
     * 控制人证件类型
     */
    private String controllerCredType;
    /**
     * 控制人证件号码
     */
    private String controllerCredCode;
    /**
     * 控制人证件有效期
     */
    private String controllerCertTerm;
    /**
     * 控制人反欺诈结果
     */
    private String controllerAntifraudResult;
    /**
     * 对应核心企业
     */
    private String coreEnterprise;
    /**
     * 意向业务
     */
    private String intentBusiness;
    /**
     * 所属城市
     */
    private String belongCity;
    /**
     * 数据来源 1 CRM，2 ABS，3 ERP，4 BGY
     */
    private String dataSource;
    /**
     * 数据来源ID
     */
    private String relatedSourceId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 最后跟进时间
     */
    private Date lastFollowDate;
    /**
     * 是否删除(0:未删除,1:删除)
     */
    @TableLogic
    private String isDeleted;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 来源数据创建时间
     */
    private Date relatedCreateTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;


}
