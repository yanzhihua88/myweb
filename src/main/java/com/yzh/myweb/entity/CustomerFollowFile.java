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
 * 客户跟进日志对应文件表
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
@Data
@Accessors(chain = true)
@TableName("t_customer_follow_file")
public class CustomerFollowFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;
    /**
     * 跟进日志ID
     */
    private Integer logId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 分区名称
     */
    private String bucketName;
    /**
     * 对象名称
     */
    private String objectName;
    /**
     * 文件MD5
     */
    private String fileMd5;
    /**
     * 文件扩展名
     */
    private String fileExt;
    /**
     *  是否删除(0:未删除,1:删除)
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
