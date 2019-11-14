package com.chuangxin.musicroom.core.dto;

import lombok.Data;

@Data
public class WxUserInfo {

    private String openid;

    private String nickname;

    private String headimgurl;

    private Integer sex;// 1男，2女

    private String city;

    private String country;

    private String province;

}
