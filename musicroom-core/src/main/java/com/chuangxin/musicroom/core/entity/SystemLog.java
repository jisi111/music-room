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
@TableName("system_log")
public class SystemLog extends Model<SystemLog> {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value="id", type= IdType.INPUT)
    @JsonInclude(Include.NON_NULL)
    private Integer id;
    
    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    @JsonInclude(Include.NON_NULL)
    private String ip;
    
    /**
     * 模块
     */
    @ApiModelProperty(value = "模块")
    @JsonInclude(Include.NON_NULL)
    private String module;
    
    /**
     * 接口
     */
    @ApiModelProperty(value = "接口")
    @JsonInclude(Include.NON_NULL)
    private String api;
    
    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    @JsonInclude(Include.NON_NULL)
    private String param;
    
    /**
     * 结果编码，0失败，1成功
     */
    @ApiModelProperty(value = "结果编码，0失败，1成功")
    @TableField("result_code")
    @JsonInclude(Include.NON_NULL)
    private String resultCode;
    
    /**
     * 结果内容
     */
    @ApiModelProperty(value = "结果内容")
    @TableField("result_data")
    @JsonInclude(Include.NON_NULL)
    private String resultData;
    
    /**
     * 耗时，单位毫秒
     */
    @ApiModelProperty(value = "耗时，单位毫秒")
    @JsonInclude(Include.NON_NULL)
    private Integer duration;
    
    /**
     * 级别，1debug，2info，3warn，4error
     */
    @ApiModelProperty(value = "级别，1debug，2info，3warn，4error")
    @JsonInclude(Include.NON_NULL)
    private Integer level;
    
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
