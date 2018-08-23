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
 * 用户表
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
@Data
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID(域账户ID)
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户级别 1Boss 2主办 3协办
     */
    private Integer userLevel;

    /**
     * 用户职位
     */
    private String job;

    /**
     * 上级用户ID
     */
    private Integer parentUserId;
    /**
     * 负责板块((1基建工程,2医药医疗,3能源化工,4其他)
     */
    private String responsiblePlate;
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
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;


}
