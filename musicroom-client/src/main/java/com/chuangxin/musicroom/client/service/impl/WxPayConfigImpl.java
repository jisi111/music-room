package com.chuangxin.musicroom.client.service.impl;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WxPayConfigImpl implements WXPayConfig {

    private byte[] certData;
    private String appId;
    private String appSecret;
    private String mchId;
    private String mchSecret;

    public WxPayConfigImpl(String certPath, String appId, String appSecret,
                           String mchId, String mchSecret) throws Exception{
        this.appId = appId;
        this.appSecret = appSecret;
        this.mchId = mchId;
        this.mchSecret = mchSecret;
        if (certPath != null) {
            File file = new File(certPath);
            InputStream certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        }
    }

    public String getAppID() {
        return appId;
    }

    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {// 注意这里是商家secret，不是公众号secret
        return mchSecret;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getMchSecret() {
        return mchSecret;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
