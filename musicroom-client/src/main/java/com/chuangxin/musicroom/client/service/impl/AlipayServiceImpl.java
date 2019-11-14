package com.chuangxin.musicroom.client.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.chuangxin.musicroom.client.service.*;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.core.dto.BaseErrResult;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.entity.*;
import com.chuangxin.musicroom.core.exception.CommonError;
import com.chuangxin.musicroom.core.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    HttpServletRequest request;
    @Autowired
    SessionService sessionService;
    @Autowired
    OrdersService ordersService;

    String GATE_WAY = "https://openapi.alipay.com/gateway.do";
    String APP_ID = "2019082866502159";
    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiSQtNZKX4er/p4OpApKoDn6dijT59m07z9UbD4sMwPsdC0vZk/D9hKPdwH/aihyxUG4ulWU02AhZ+VtVqr92asWQXacAuzK9o3la1+y57c9COVNg9oFqDrvmsSY+GI3O7Bt2XqNhjoj7gqEZZluY/nua+r5RZgD/69ds9e7YH19TBFaNezlXWnqupoDGw4yqLrTuIJmJKNGk0Q+uEY2w42KXZQ51EJX64UykdL1VTvZyXZPhQkSD5byShwhx58TGsSyd9Ca88pqtfvUxXE0TN2ZbMY//zKIeP054CRMV5vEf/VTwwazuWqc/ci0iQ0BNXddfLtex2Zi/6VK8HN8gXQIDAQAB";
    String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC/5qnJQ7W6ixxOSo6VfrP793JQafkH1/VtUmv/VfhZyHb822dFwbRfmirxmhMqrtjzXNome5Uc9yD9r7BA8+4FFYUlfyjTAe4zXCF2xW5g89nS2CausKzi/NzJ34oGR/PeZuLspIjB9IvuKwuf+SL136v3jf779dgHstJcDR48uQrEvEmYcgr3v+WLKTw8pnLRNoLZtZQh9K7qQ5i/HK+gDUZwLnhnHEIG4qMARb7R1KDvGDedgUzEdPVHRVFgihOCpL3YN1pc7stIdRE65XWrK9BfJ7yNoDoQZcW5Qqs2W3RuNBZ1r051j7f4zsYf2P1VCUl5rhGpFw++Oqi2N/6tAgMBAAECggEAcQ+hcObnpuRW5PmgUAu6VvVO8Hm/MxPVkXdCEcnA7ITjNc6+ZabecoUOUBIKwNcLEzbdgFbHX61qToy4N0yAK5amP5VPpCI7CR96x9NCWPTSnRqkj7+f3IxGONnvJAw4bgwIHoxB1qQR3VyXGMVcB6ikVNHgLOqHQOHmdj10VylZIrNKUQ2CHtEGGRb0B7ElKf1uA73c9gIBHTEBXRZK+/haP1q8dJ84suPf2DWV3xi5KrFDF0+M7z0ccmGSQkhWQKWSCgHZvnfCsdblYMSnOFVMIs8aXrHe8YJwLWyX3OOGbCwmhhi1PwwhsmjMRoqeHGZb2cgAVYsPFYP7iJhzsQKBgQDjDf7WbZvTPX9ZVkpEiPxSwud011VMIewSVtH8O+o081Z7E8HGuNOtUu2Np+hyXE+m2PXLquR+ulOTSBhBYw1xTwwfTkgaa/4MChJW+S0CpG0QMOge2+HbICaUFSJLDyWzHV0x5sHY4FaWiQXLfqrZxiGX26SlHWDs9HCCC/Nc2wKBgQDYXWrAw2ixzFFvXXCiyv6c8gwrrXxO+c7ozOz3doye/g5ZfCmtu8jS9WEHeXqQL/CKkpq7c520dJ9GylWUxy7EPz1fL0oA9BYbO1oPhYTycxp7LJv6bHicmicnxsiIMF0QDnEyP2zJkvyAGNyWEbaHOlQvvyld7D/MYaabYH0lFwKBgBrBrHVCZ2mGRrqVY50d83YhR7tKIOAe4z3qA0bRHLFut5M7actsPm2fZyUZU1gerrg/uv9sPQ7+EBk8Frel35Cpuwj5ZZ/PeBmdGjorlPVJwRx2VvRD/yKecE1lDCyCNXQdEr1trwxKq4mDzi4D2Ehfg9fsZ0OZ6bEiwRtiIySfAoGBAMdmIHISJLRnJk3KAXomNkyRzaGbOsrV8lgeM+1D9gi4/qR9hzOzsjqpJYjr7YZgiABNaAiFXrvmFrv40LJSdskptve37y29xXnHUxKm7IU8ixUlYYKC4Q0CDD/eNQU9/SZFCRFT3oBO1CGByBkNrfgmS1vNguQqTAmhnRfo7qx1AoGAV+wlO4msxMYDalgiEfatQFp/dA6IVyIL28SXXnxbfzCdEUNaVYm1/Rooz468WwXJ/fAsaLBNY5PYJEek3sZhd3TlyUk3F8OFXVSO3z/m7fuIxCbMfApfjeFkA9D6/p46HChWrKw3jlZOqeaHZKejHRJhVHONJh+Vr4l2x6JleHY=";
    String CHARSET = "UTF-8";

    @Value("${alipay.payCallback}")
    String PAY_CALLBACK;

    /**
     * 购买商品订单，获取签名
     */
    @Override
    public BaseResult getPayInfo(Map<String, Object> param) {
        Integer goodsId = (Integer) param.get("goodsId");
        String goodsTitle = (String) param.get("goodsTitle");
        if (goodsTitle.length() > 8) {
            goodsTitle = goodsTitle.substring(0, 8);
        }
        String totalAmount = (String) param.get("totalAmount");
        String oldOrdersId = null;
        if (param.get("oldOrdersId") != null) {
            oldOrdersId = (String) param.get("oldOrdersId");
        }
        Integer rentTime = param.get("rentTime") == null ? 0 : (Integer) param.get("rentTime");

//        // 生成订单
//        Orders orders = ordersService.addOrUpdateOrders(goodsId, goodsTitle,
//                Float.valueOf(totalAmount), oldOrdersId, rentTime);
//
//        // 签名
//        BaseResult signResult = getSign(goodsTitle, "goods", orders.getId(), totalAmount);
//        return signResult;
        return null;
    }

    /**
     * passbackParams：自定义参数，“goods”商品订单，“deposit”退押金订单
     */
    private BaseResult getSign(String goodsTitle, String passbackParams, String ordersId, String totalAmount) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("商品");
        model.setSubject(goodsTitle);// 商品标题、订单标题等
        model.setPassbackParams(passbackParams);// 自定义参数
        model.setOutTradeNo(ordersId);// 商家本地订单ID
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");// 固定值
        request.setBizModel(model);
        request.setNotifyUrl(PAY_CALLBACK);
        String sign = null;
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//            log.debug(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            sign = response.getBody();
        } catch (AlipayApiException e) {
            log.error("alipay sign err ", e);
            CommonError.CommonErr(new BaseErrResult("-1", e.getErrMsg()));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        map.put("ordersId", ordersId);
        return new BaseResult(map);
    }

    @Override
    public String payCallback() {
        // 获取验签参数
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");// 出现乱码时使用
            params.put(name, valueStr);
            // log.debug("name " + name + " value " + valueStr);
        }

        // 验签
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET,"RSA2");
        } catch (AlipayApiException e) {
            log.error("rsaCheck fail ", e);
            return "rsaCheck fail";
        }
        if (!flag) {
            return "sign check fail";
        }

        String passbackParams = request.getParameter("passback_params");
        String tradeStatus = request.getParameter("trade_status");
        String appId = request.getParameter("app_id");
        String sellerId = request.getParameter("seller_id");
        String buyerId = request.getParameter("buyer_id");// 买家支付宝ID
        String buyerLogonId = request.getParameter("buyer_logon_id");// 买家支付宝账号（已加密）
        String totalAmountStr = request.getParameter("total_amount");
        Float totalAmount = Float.valueOf(totalAmountStr);
        String tradeNo = request.getParameter("trade_no");// 支付宝订单编号
        String outTradeNo = request.getParameter("out_trade_no");// 商家系统订单编号
        String gmtCreate = request.getParameter("gmt_create");// 交易创建时间
        String gmtPayment = request.getParameter("gmt_payment");// 交易付款时间
        log.debug("tradeStatus " + tradeStatus + " appId " + appId + " sellerId " + sellerId
                + " buyerId " + buyerId + " buyerLogonId " + buyerLogonId + " totalAmount " + totalAmount
                + " tradeNo " + tradeNo + " outTradeNo " + outTradeNo + " gmtCreate " + gmtCreate
                + " gmtPayment " + gmtPayment);

        if (!tradeStatus.equals("TRADE_SUCCESS")) {
            return "status fail";
        }
        // appId检查
        if (!appId.equals(APP_ID)) {
            log.error("appId not match");
            return "appId not match";
        }

        // 更新订单状态
        if (passbackParams.equals("goods")) {// 商品订单
            Orders orders = ordersService.getById(outTradeNo);
            if (orders == null) {// 订单为空可能是测试时删除了，返回成功给alipay
                log.debug("alipay outTradeNo no record");
                return "success";
            }
            // 总额检查
            if (!orders.getTotalAmount().equals(totalAmount)) {
                log.error("amount err");
                return "amount err";
            }
            // 更新订单
            orders.setAliBuyerId(buyerId);
            orders.setAliBuyerLogonId(buyerLogonId);
            orders.setAliOrdersId(tradeNo);
            try {
                orders.setUpdateTime(DateTimeUtil.datetimeFormat.parse(gmtPayment));
            } catch (ParseException e) {
                e.printStackTrace();
                log.error("err", e);
            }
            orders.setPayType(1);
            orders.setStatus(2);// // 1未付款，2已付款，3已发货，4已签收
            ordersService.updateById(orders);

        }

        return "success";
    }
}
