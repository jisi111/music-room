package com.chuangxin.musicroom.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author DaiDong
 * @since 2019-08-14
 * @email 755144556@qq.com
 * 阿里云OSS
 */
@Api(tags = "AliOssController", description = "")
@RestController
@Slf4j
@RequestMapping("/aliOss")
public class AliOssController {

    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";

    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAI1lQTsICzmKB9";
    private static String accessKeySecret = "DyAuIAXeRNjcIaQXFRVrCsh7OV9qBN";

    /**
     * 签名
     */
    @ApiOperation(value="签名", notes="")
    @RequestMapping(value="/getSign", method={RequestMethod.POST})
    public String getSign(HttpServletRequest request) throws Exception {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String param = (String) paramNames.nextElement();
            String value = request.getParameter(param);
            log.debug(param + ":" + value);
        }
        String content = request.getParameter("content");
        String strSign = "";
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(accessKeySecret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
            mac.init(secretKey);

            byte[] sigBuf = mac.doFinal(content.getBytes(CONTENT_CHARSET));
            strSign = base64Encode(sigBuf);
            strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            throw e;
        }
        return "OSS " + accessKeyId + ":" + strSign;
    }

    private String base64Encode(byte[] buffer) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(buffer);
    }

}
