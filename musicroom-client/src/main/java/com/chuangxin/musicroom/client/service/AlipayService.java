package com.chuangxin.musicroom.client.service;

import com.chuangxin.musicroom.core.dto.BaseResult;
import java.util.Map;

public interface AlipayService {

    BaseResult getPayInfo(Map<String, Object> param);

    String payCallback();

}
