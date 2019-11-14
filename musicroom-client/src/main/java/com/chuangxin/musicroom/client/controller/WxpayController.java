package com.chuangxin.musicroom.client.controller;

import com.chuangxin.musicroom.client.service.WxpayService;
import com.chuangxin.musicroom.core.dto.BaseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DaiDong
 * @since 2019-08-14
 * @email 755144556@qq.com
 */
@Api(tags = "WxController", description = "")
@RestController
@Slf4j
@RequestMapping("/wxpay")

public class WxpayController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;
    @Autowired
    WxpayService wxpayService;

    /**
     * 统一下单
     */
    @ApiOperation(value="统一下单", notes="")
    @RequestMapping(value = "/doUnifiedOrder", method = {RequestMethod.POST})
    public BaseResult doUnifiedOrder(@RequestBody Map<String, Object> param) {
        return wxpayService.doUnifiedOrder(param);
    }

    /**
     * 支付结果通知
     */
    @RequestMapping(value = "/payNotify", method = {RequestMethod.POST})
    public String payNotify() throws Exception {
        return wxpayService.payNotify();
    }
}
