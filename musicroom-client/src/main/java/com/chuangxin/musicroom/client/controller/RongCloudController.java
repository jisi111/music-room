package com.chuangxin.musicroom.client.controller;

import com.chuangxin.musicroom.client.service.RongCloudService;
import com.chuangxin.musicroom.core.dto.RegistRcUserParam;
import com.chuangxin.musicroom.core.dto.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DaiDong
 * @since 2019-08-14
 * @email 755144556@qq.com
 * 融云
 */
@Api(tags = "RongCloudController", description = "")
@RestController
@Slf4j
@RequestMapping("/rongCloud")
public class RongCloudController {

    @Autowired
    RongCloudService rongCloudService;

    /**
     * 注册融云用户
     */
    @ApiOperation(value="注册融云用户", notes="")
    @RequestMapping(value="/registRcUser", method={RequestMethod.POST})
    public BaseResult registRcUser(@RequestBody RegistRcUserParam param) throws Exception {
        String rcToken = rongCloudService.registRcUser(param.getId() + "",
                param.getName(), param.getPortrait());
        return new BaseResult(rcToken);
    }

    /**
     * 发送系统消息
     */
    @ApiOperation(value="发送系统消息", notes="")
    @RequestMapping(value="/sendSystemMsg", method={RequestMethod.POST})
    public BaseResult sendSystemMsg(@RequestBody String[] targetIds) throws Exception {
        return rongCloudService.sendSystemMsg(targetIds, "3", "通知", "通知内容");
    }

    /**
     * 发送聊天消息
     */
    @ApiOperation(value="发送聊天消息", notes="")
    @RequestMapping(value="/sendPrivateMsg", method={RequestMethod.POST})
    public BaseResult sendPrivateMsg(@RequestBody String[] targetIds) throws Exception {
        return rongCloudService.sendNoticeMsg(targetIds, "title", "hello world");
    }

}
