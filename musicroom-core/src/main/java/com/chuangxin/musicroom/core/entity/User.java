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
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value="id", type= IdType.INPUT)
    @JsonInclude(Include.NON_NULL)
    private Integer id;

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    @TableField(exist = false)
    @JsonInclude(Include.NON_NULL)
    private String token;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    @JsonInclude(Include.NON_NULL)
    private String nickName;
    
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @TableField("user_name")
    @JsonInclude(Include.NON_NULL)
    private String userName;
    
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    @JsonInclude(Include.NON_NULL)
    private String phone;

    /**
     * 微信openId
     */
    @ApiModelProperty(value = "微信openId")
    @TableField("wx_open_id")
    @JsonInclude(Include.NON_NULL)
    private String wxOpenId;
    
    /**
     * 融云token
     */
    @ApiModelProperty(value = "融云token")
    @TableField("rc_token")
    @JsonInclude(Include.NON_NULL)
    private String rcToken;
    
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @JsonInclude(Include.NON_NULL)
    private String password;
    
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @JsonInclude(Include.NON_NULL)
    private String image;
    
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @JsonInclude(Include.NON_NULL)
    private Integer sex;
    
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date birthday;
    
    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    @JsonInclude(Include.NON_NULL)
    private String sign;
    
    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    @JsonInclude(Include.NON_NULL)
    private Float account;
    
    /**
     * 支付宝账号
     */
    @ApiModelProperty(value = "支付宝账号")
    @TableField("alipay_account")
    @JsonInclude(Include.NON_NULL)
    private String alipayAccount;
    
    /**
     * 支付宝收款人实名
     */
    @ApiModelProperty(value = "支付宝收款人实名")
    @TableField("alipay_real_name")
    @JsonInclude(Include.NON_NULL)
    private String alipayRealName;
    
    /**
     * 上次浏览博客时间
     */
    @ApiModelProperty(value = "上次浏览博客时间")
    @TableField("last_blog_visit")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date lastBlogVisit;
    
    /**
     * 认证等级
     */
    @ApiModelProperty(value = "认证等级")
    @TableField("auth_level")
    @JsonInclude(Include.NON_NULL)
    private Integer authLevel;
    
    /**
     * 徽章等级
     */
    @ApiModelProperty(value = "徽章等级")
    @TableField("badge_level")
    @JsonInclude(Include.NON_NULL)
    private Integer badgeLevel;

    /**
     * 当前plan
     */
    @ApiModelProperty(value = "当前plan")
    @TableField(exist = false)
    @JsonInclude(Include.NON_NULL)
    private Plan currentPlan;
    /**
     * 关注数
     */
    @ApiModelProperty(value = "关注数")
    @TableField("follow_num")
    @JsonInclude(Include.NON_NULL)
    private Integer followNum;
    
    /**
     * 粉丝数
     */
    @ApiModelProperty(value = "粉丝数")
    @TableField("fan_num")
    @JsonInclude(Include.NON_NULL)
    private Integer fanNum;
    
    /**
     * 获赞数
     */
    @ApiModelProperty(value = "获赞数")
    @TableField("likes_num")
    @JsonInclude(Include.NON_NULL)
    private Integer likesNum;
    
    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数")
    @TableField("collect_num")
    @JsonInclude(Include.NON_NULL)
    private Integer collectNum;
    
    /**
     * 区ID
     */
    @ApiModelProperty(value = "区ID")
    @TableField("district_id")
    @JsonInclude(Include.NON_NULL)
    private Integer districtId;

    /**
     * 区行政编码
     */
    @ApiModelProperty(value = "区行政编码")
    @TableField(exist = false)
    @JsonInclude(Include.NON_NULL)
    private String districtCode;
    
    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    @TableField("district_name")
    @JsonInclude(Include.NON_NULL)
    private String districtName;
    
    /**
     * 城市ID
     */
    @ApiModelProperty(value = "城市ID")
    @TableField("city_id")
    @JsonInclude(Include.NON_NULL)
    private Integer cityId;
    
    /**
     * 城市名
     */
    @ApiModelProperty(value = "城市名")
    @TableField("city_name")
    @JsonInclude(Include.NON_NULL)
    private String cityName;
    
    /**
     * 省ID
     */
    @ApiModelProperty(value = "省ID")
    @TableField("province_id")
    @JsonInclude(Include.NON_NULL)
    private Integer provinceId;
    
    /**
     * 省名
     */
    @ApiModelProperty(value = "省名")
    @TableField("province_name")
    @JsonInclude(Include.NON_NULL)
    private String provinceName;
    
    @TableField("contact_name")
    @JsonInclude(Include.NON_NULL)
    private String contactName;
    
    @TableField("contact_phone")
    @JsonInclude(Include.NON_NULL)
    private String contactPhone;
    
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @TableField("contact_address")
    @JsonInclude(Include.NON_NULL)
    private String contactAddress;
    
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
