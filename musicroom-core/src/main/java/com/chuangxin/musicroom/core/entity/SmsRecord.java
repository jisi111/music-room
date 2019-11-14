package com.chuangxin.musicroom.core.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
 
/**
 * @author DaiDong
 * @since 2019-09-23 
 * @email 755144556@qq.com
 */
@Data
@TableName("sms_record")
public class SmsRecord extends Model<SmsRecord> {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value="id", type= IdType.INPUT)
    @JsonInclude(Include.NON_NULL)
    private Integer id;
    
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    @JsonInclude(Include.NON_NULL)
    private String phone;
    
    /**
     * 业务类型，1登录，2找回密码
     */
    @ApiModelProperty(value = "业务类型，1登录，2找回密码")
    @TableField("business_type")
    @JsonInclude(Include.NON_NULL)
    private Integer businessType;
    
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    @TableField("check_code")
    @JsonInclude(Include.NON_NULL)
    private String checkCode;
    
    /**
     * 结果，0失败，1成功
     */
    @ApiModelProperty(value = "结果，0失败，1成功")
    @JsonInclude(Include.NON_NULL)
    private Integer result;
    
    /**
     * 是否有效，0无效，1有效
     */
    @ApiModelProperty(value = "是否有效，0无效，1有效")
    @TableField("is_valid")
    @JsonInclude(Include.NON_NULL)
    private Integer isValid;
    
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField("create_user_id")
    @JsonInclude(Include.NON_NULL)
    private Integer createUserId;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value="create_time", fill=FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date createTime;
    
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @TableField("update_user_id")
    @JsonInclude(Include.NON_NULL)
    private Integer updateUserId;
    
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value="update_time", fill=FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date updateTime;
    

}
