package com.chuangxin.musicroom.client.service.impl;

import com.chuangxin.musicroom.client.service.SmsSendService;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.util.JsonObjUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DaiDong
 * @since 2019-03-26
 * @email 755144556@qq.com
 */
@Service
@Transactional
@Slf4j
public class SmsSendServiceImpl implements SmsSendService {

    @Value("${sms.accessKey}")
    String accessKey;
    @Value("${sms.accessSecret}")
    String accessSecret;
    @Value("${sms.sign}")
    String sign;
    @Value("${sms.checkCodeTemplate}")
    String checkCodeTemplate;
    @Value("${sms.ordersTemplate}")
    String ordersTemplate;

    /**
     * 发送短信
     * businessType：1验证码，2订单更新
     */
    public BaseResult send(String phone, String text, Integer businessType) throws Exception {
        String templateId = checkCodeTemplate;
        if (businessType == 1) {
            templateId = checkCodeTemplate;
        } else if (businessType == 2) {
            templateId = ordersTemplate;
        }
        BaseResult sendResult = aliSend(phone, text, templateId);
        return sendResult;
    }
    
    /**
     * 阿里云短信发送
     */
    private BaseResult aliSend(String phone, String msgText, String templateId) throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("default", 
                accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", sign);
        request.putQueryParameter("TemplateCode", templateId);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + msgText + "\"}");

        CommonResponse response = client.getCommonResponse(request);
        log.debug("sms response " + response.getData());
        Map<String, Object> responseData = JsonObjUtils.json2map(response.getData());
        if (((String) responseData.get("Code")).equals("OK")) {
            return new BaseResult();
        } else {
            return new BaseResult("0", (String) responseData.get("Message"), null);
        }        
    }
    
}
