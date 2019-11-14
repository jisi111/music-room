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
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value="id", type= IdType.INPUT)
    @JsonInclude(Include.NON_NULL)
    private String id;
    
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    @TableField("goods_id")
    @JsonInclude(Include.NON_NULL)
    private Integer goodsId;
    
    /**
     * 卖家ID
     */
    @ApiModelProperty(value = "卖家ID")
    @TableField("seller_id")
    @JsonInclude(Include.NON_NULL)
    private Integer sellerId;
    
    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    @TableField("buyer_id")
    @JsonInclude(Include.NON_NULL)
    private Integer buyerId;
    
    /**
     * 状态，1未付款，2已付款，3已发货，4已签收，5租户申请归还，6东家已退押金至平台，7租户已发货，8东家已收货
     */
    @ApiModelProperty(value = "状态，1未付款，2已付款，3已发货，4已签收，5租户申请归还，6东家已退押金至平台，7租户已发货，8东家已收货")
    @JsonInclude(Include.NON_NULL)
    private Integer status;
    
    /**
     * 交易类型，1租，2售
     */
    @ApiModelProperty(value = "交易类型，1租，2售")
    @TableField("trade_type")
    @JsonInclude(Include.NON_NULL)
    private Integer tradeType;
    
    /**
     * 起租时间
     */
    @ApiModelProperty(value = "起租时间")
    @TableField("rent_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date rentStartTime;
    
    /**
     * 完租时间
     */
    @ApiModelProperty(value = "完租时间")
    @TableField("rent_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date rentEndTime;
    
    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    @TableField("goods_price")
    @JsonInclude(Include.NON_NULL)
    private Float goodsPrice;
    
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    @TableField("total_amount")
    @JsonInclude(Include.NON_NULL)
    private Float totalAmount;
    
    /**
     * 押金
     */
    @ApiModelProperty(value = "押金")
    @JsonInclude(Include.NON_NULL)
    private Float deposit;
    
    @TableField("is_deposit_free")
    @JsonInclude(Include.NON_NULL)
    private Integer isDepositFree;
    
    /**
     * 是否包邮，0否，1是
     */
    @ApiModelProperty(value = "是否包邮，0否，1是")
    @TableField("is_ship_free")
    @JsonInclude(Include.NON_NULL)
    private Integer isShipFree;
    
    /**
     * 邮费
     */
    @ApiModelProperty(value = "邮费")
    @TableField("ship_price")
    @JsonInclude(Include.NON_NULL)
    private Float shipPrice;
    
    /**
     * 租期
     */
    @ApiModelProperty(value = "租期")
    @TableField("rent_time")
    @JsonInclude(Include.NON_NULL)
    private Integer rentTime;
    
    /**
     * 支付类型，1支付宝，2微信
     */
    @ApiModelProperty(value = "支付类型，1支付宝，2微信")
    @TableField("pay_type")
    @JsonInclude(Include.NON_NULL)
    private Integer payType;
    
    /**
     * 支付宝交易ID
     */
    @ApiModelProperty(value = "支付宝交易ID")
    @TableField("ali_orders_id")
    @JsonInclude(Include.NON_NULL)
    private String aliOrdersId;
    
    /**
     * 支付宝买家ID
     */
    @ApiModelProperty(value = "支付宝买家ID")
    @TableField("ali_buyer_id")
    @JsonInclude(Include.NON_NULL)
    private String aliBuyerId;
    
    /**
     * 支付宝买家账号
     */
    @ApiModelProperty(value = "支付宝买家账号")
    @TableField("ali_buyer_logon_id")
    @JsonInclude(Include.NON_NULL)
    private String aliBuyerLogonId;
    
    /**
     * 微信交易ID
     */
    @ApiModelProperty(value = "微信交易ID")
    @TableField("wx_orders_id")
    @JsonInclude(Include.NON_NULL)
    private String wxOrdersId;
    
    /**
     * 微信openId
     */
    @ApiModelProperty(value = "微信openId")
    @TableField("wx_open_id")
    @JsonInclude(Include.NON_NULL)
    private String wxOpenId;
    
    /**
     * 送货地址
     */
    @ApiModelProperty(value = "送货地址")
    @TableField("ship_address")
    @JsonInclude(Include.NON_NULL)
    private String shipAddress;
    
    /**
     * 送货联系人
     */
    @ApiModelProperty(value = "送货联系人")
    @TableField("ship_contact")
    @JsonInclude(Include.NON_NULL)
    private String shipContact;
    
    /**
     * 送货电话
     */
    @ApiModelProperty(value = "送货电话")
    @TableField("ship_phone")
    @JsonInclude(Include.NON_NULL)
    private String shipPhone;
    
    /**
     * 发货提醒次数
     */
    @ApiModelProperty(value = "发货提醒次数")
    @TableField("ship_remind_num")
    @JsonInclude(Include.NON_NULL)
    private Integer shipRemindNum;
    
    /**
     * 发货提醒时间
     */
    @ApiModelProperty(value = "发货提醒时间")
    @TableField("ship_remind_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date shipRemindTime;
    
    /**
     * 提醒签收时间
     */
    @ApiModelProperty(value = "提醒签收时间")
    @TableField("receive_remind_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(Include.NON_NULL)
    private Date receiveRemindTime;
    
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
