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
public class Plan extends Model<Plan> {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value="id", type= IdType.INPUT)
    @JsonInclude(Include.NON_NULL)
    private Integer id;
    
    /**
     * 作者ID
     */
    @ApiModelProperty(value = "作者ID")
    @TableField("author_id")
    @JsonInclude(Include.NON_NULL)
    private Integer authorId;
    
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @JsonInclude(Include.NON_NULL)
    private String title;
    
    /**
     * 封面
     */
    @ApiModelProperty(value = "封面")
    @JsonInclude(Include.NON_NULL)
    private String cover;
    
    /**
     * 周期，单位天
     */
    @ApiModelProperty(value = "周期，单位天")
    @JsonInclude(Include.NON_NULL)
    private Integer period;
    
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date startTime;
    
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date endTime;
    
    /**
     * 加油数
     */
    @ApiModelProperty(value = "加油数")
    @TableField("support_num")
    @JsonInclude(Include.NON_NULL)
    private Integer supportNum;
    
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
