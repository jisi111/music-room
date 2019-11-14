package com.chuangxin.musicroom.client.controller;

import com.chuangxin.musicroom.client.service.AlipayService;
import com.chuangxin.musicroom.core.dto.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author DaiDong
 * @since 2019-08-14
 * @email 755144556@qq.com
 * 支付宝
 */
@Api(tags = "AlipayController", description = "")
@RestController
@Slf4j
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    AlipayService alipayService;

    /**
     * 支付签名
     */
    @Synchronized
    @ApiOperation(value="获取支付签名", notes="")
    @RequestMapping(value="/getPayInfo", method={RequestMethod.POST})
    public BaseResult getPayInfo(@RequestBody Map<String, Object> param) throws Exception {
        return alipayService.getPayInfo(param);
    }

    /**
     * 支付回调
     */
    @ApiOperation(value="支付回调", notes="")
    @RequestMapping(value="/payCallback", method={RequestMethod.POST})
    public String payCallback() throws Exception {
        return alipayService.payCallback();
    }

}
