package com.chuangxin.musicroom.client.service.impl;

import com.chuangxin.musicroom.client.service.*;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.entity.Orders;
import com.chuangxin.musicroom.core.util.Arith;
import com.chuangxin.musicroom.core.util.RequestUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @author DaiDong
 * @since 2019-09-02
 * @email 755144556@qq.com
 */
@Service
@Transactional
@Slf4j
public class WxpayServiceImpl implements WxpayService {

    @Value("${wxpay.appId}")
    String appId;
    @Value("${wxpay.appSecret}")
    String appSecret;
    @Value("${wxpay.mchId}")
    String mchId;
    @Value("${wxpay.mchSecret}")
    String mchSecret;
    @Value("${wxpay.notifyUrl}")
    String notifyUrl;

    @Autowired
    HttpServletRequest request;
    @Autowired
    SessionService sessionService;
    @Autowired
    OrdersService ordersService;

    /**
     * 统一下单
     */
    @Override
    public BaseResult doUnifiedOrder(Map<String, Object> param) {
        Integer goodsId = (Integer) param.get("goodsId");
        String goodsTitle = (String) param.get("goodsTitle");
        String totalAmountStr = (String) param.get("totalAmount");
        Float totalAmount = Float.valueOf(totalAmountStr);
        String oldOrdersId = null;
        if (param.get("oldOrdersId") != null) {
            oldOrdersId = (String) param.get("oldOrdersId");
        }
        Integer rentTime = param.get("rentTime") == null ? 0 : (Integer) param.get("rentTime");

        // 生成订单
//        Orders orders = ordersService.addOrUpdateOrders(goodsId, goodsTitle, totalAmount,
//                oldOrdersId, rentTime);

//        BaseResult signResult = getSign(goodsTitle, "goods", goodsId, orders.getId(),
//                Float.valueOf(totalAmount));
//        return signResult;
        return null;
    }

    /**
     * passbackParams：自定义参数，“goods”商品订单，“deposit”退押金订单
     */
    private BaseResult getSign(String goodsTitle, String passbackParams, Integer goodsId,
                               String ordersId, Float totalAmount) {
        WXPay wxpay;
        WxPayConfigImpl wxPayConfig;

        // 初始化
        try {
            wxPayConfig = new WxPayConfigImpl(null, appId, appSecret, mchId, mchSecret);
            wxpay = new WXPay(wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult("0", e.getMessage(), null);
        }

        Integer totalAmountFen = (int) Arith.mul(totalAmount, 100);
        String clientIp = RequestUtil.getIpAddr(request);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "享啦-商品购买");
        data.put("attach", passbackParams);
        data.put("out_trade_no", ordersId);
        data.put("device_info", "APP");
        data.put("fee_type", "CNY");
        data.put("total_fee", totalAmountFen + "");
        data.put("spbill_create_ip", clientIp);
        data.put("notify_url", notifyUrl);
        data.put("trade_type", "APP");  // 此处指定为扫码支付
        if (goodsId != null) {
            data.put("product_id", goodsId + "");
        }
        log.debug("sign raw data " + data.toString());

        // 获取prepayId
        String prepayId = null;
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            log.debug("unifiedOrder resp " + resp.toString());
            if ("FAIL".equals(resp.get("return_code"))) {
                return new BaseResult("0", resp.get("return_msg"), null);
            }
            prepayId = resp.get("prepay_id");
        } catch (Exception e) {
            log.error("wx unifiedOrder fail ", e);
            return new BaseResult("0", "wx unifiedOrder fail " + e.getMessage(), null);
        }

        // 支付参数签名，用于下一步支付
        HashMap<String, String> payData = new HashMap<String, String>();
        payData.put("appid", wxPayConfig.getAppID());
        payData.put("partnerid", wxPayConfig.getMchID());
        payData.put("prepayid", prepayId);
        payData.put("package", "Sign=WXPay");
        payData.put("noncestr", WXPayUtil.generateNonceStr());
        payData.put("timestamp", System.currentTimeMillis() / 1000 + "");
        try {
            // 注意这里是传入mchSecret
            payData.put("sign", WXPayUtil.generateSignature(payData, wxPayConfig.getKey()));
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult("0", e.getMessage(), null);
        }
        payData.put("ordersid", ordersId);// 这个字段不参与签名，仅仅给前端使用
        log.debug("sign map " + payData.toString());

        return new BaseResult("1", "成功", payData);
    }

    @Override
    public String payNotify() throws Exception {
        // 读取数据流
        ServletInputStream instream = null;
        StringBuffer sb = null;
        try {
            instream = request.getInputStream();
            sb = new StringBuffer();
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = instream.read(buffer)) != -1) {
                sb.append(new String(buffer,0,len));
            }
            instream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return returnXml(WXPayConstants.FAIL, "文件流读取错误");
        }

        // 解析xml
        log.info("payNotify string " + sb.toString());
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(sb.toString());

        // 支付错误信息，返回微信已经收到
        if (!WXPayConstants.SUCCESS.equals(notifyMap.get("return_code"))) {
            return returnXml(WXPayConstants.SUCCESS, "OK");
        }

        // 验证签名
        try {
            if (!WXPayUtil.isSignatureValid(notifyMap, mchSecret, WXPayConstants.SignType.MD5)) {
                return returnXml(WXPayConstants.FAIL, "签名验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnXml(WXPayConstants.FAIL, "签名验证失败");
        }

        // 取值
        String passbackParams = notifyMap.get("attach");
        String ordersId = notifyMap.get("out_trade_no");// 订单id，doUnifiedOrder时传给微信支付
        int notifyTotalFee = Integer.valueOf(notifyMap.get("total_fee"));
        // String endTimeStr = notifyMap.get("time_end");
        // Date endTime = DateTimeUtil.yyyymmddHHmmss.parse(endTimeStr);
        String transactionId = notifyMap.get("transaction_id");
        log.info("payNotify ordersId " + ordersId);

        // 更新订单状态
        if (passbackParams.equals("goods")) {// 商品订单
            Orders orders = ordersService.getById(ordersId);

            // 测试中会删除订单，导致空指针，直接返回ok
            if (null == orders) {
                log.debug("wx outTradeNo no record");
                return returnXml(WXPayConstants.SUCCESS, "OK");
            }
            // 订单状态不是待付款的，都直接返回，防止重复支付
            if (orders.getStatus() != 1) {// 1未付款，2已付款，3已发货，4已签收
                return returnXml(WXPayConstants.SUCCESS, "OK");
            }

            // 检查支付金额
            int totalAmountFen = (int) (Arith.mul(orders.getTotalAmount(), 100));
            if (totalAmountFen != notifyTotalFee) {
                return returnXml(WXPayConstants.FAIL, "金额错误");
            }

            // 更新订单支付参数
            orders.setWxOrdersId(transactionId);
            orders.setPayType(2);
            orders.setStatus(2);// 1未付款，2已付款，3已发货，4已签收
            ordersService.updateById(orders);
        }

        return returnXml(WXPayConstants.SUCCESS, "OK");
    }

    String returnXml(String code, String msg) {
        // 返回给微信的错误值
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return_code", code);
        returnMap.put("return_msg", msg);
        String xmlData = "";
        try {
            xmlData = WXPayUtil.mapToXml(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("payNotify error " + e.getMessage());
        }
        return xmlData;
    }
}
