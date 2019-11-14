package com.chuangxin.musicroom.core.dto;

import lombok.Data;

@Data
public class LoginParam {

    private String phone;

    private String checkCode;

    private String password;

    private String districtCode;

}
