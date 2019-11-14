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
public class Region extends Model<Region> {

    private static final long serialVersionUID = 1L;
    @TableId(value="id", type= IdType.INPUT)
    @JsonInclude(Include.NON_NULL)
    private Integer id;
    
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    @JsonInclude(Include.NON_NULL)
    private String code;
    
    /**
     * 父编码
     */
    @ApiModelProperty(value = "父编码")
    @JsonInclude(Include.NON_NULL)
    private String pcode;
    
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    /**
     * 全称
     */
    @ApiModelProperty(value = "全称")
    @TableField("merger_name")
    @JsonInclude(Include.NON_NULL)
    private String mergerName;
    
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    @TableField("short_name")
    @JsonInclude(Include.NON_NULL)
    private String shortName;
    
    /**
     * 全称（简称）
     */
    @ApiModelProperty(value = "全称（简称）")
    @TableField("merger_short_name")
    @JsonInclude(Include.NON_NULL)
    private String mergerShortName;
    
    /**
     * 行政级别
     */
    @ApiModelProperty(value = "行政级别")
    @TableField("level_type")
    @JsonInclude(Include.NON_NULL)
    private String levelType;
    
    /**
     * 城市编码
     */
    @ApiModelProperty(value = "城市编码")
    @TableField("city_code")
    @JsonInclude(Include.NON_NULL)
    private String cityCode;
    
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    @TableField("zip_code")
    @JsonInclude(Include.NON_NULL)
    private String zipCode;
    
    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音")
    @JsonInclude(Include.NON_NULL)
    private String pinyin;
    
    /**
     * 简拼
     */
    @ApiModelProperty(value = "简拼")
    @JsonInclude(Include.NON_NULL)
    private String jianpin;
    
    /**
     * 首字母
     */
    @ApiModelProperty(value = "首字母")
    @TableField("first_char")
    @JsonInclude(Include.NON_NULL)
    private String firstChar;
    
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @JsonInclude(Include.NON_NULL)
    private Double lng;
    
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @JsonInclude(Include.NON_NULL)
    private Double lat;
    
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @JsonInclude(Include.NON_NULL)
    private String remark;
    

}
