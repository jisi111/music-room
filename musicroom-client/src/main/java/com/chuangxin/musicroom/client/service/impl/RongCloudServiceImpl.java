package com.chuangxin.musicroom.client.service.impl;

import com.chuangxin.musicroom.client.service.RongCloudService;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.core.dto.BaseErrResult;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.exception.CommonError;
import com.chuangxin.musicroom.core.util.JsonObjUtils;
import io.rong.RongCloud;
import io.rong.messages.InfoNtfMessage;
import io.rong.messages.TxtMessage;
import io.rong.methods.message._private.Private;
import io.rong.methods.message.system.MsgSystem;
import io.rong.models.message.PrivateMessage;
import io.rong.models.message.SystemMessage;
import io.rong.models.response.ResponseResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DaiDong
 * @since 2019-08-14
 * @email 755144556@qq.com
 */
@Service
@Transactional
@Slf4j
public class RongCloudServiceImpl implements RongCloudService {

    String appKey = "kj7swf8ok3ru2";
    String appSecret = "C7b6AmxpIIFp";

    @Autowired
    SessionService sessionService;

    /**
     * 注册融云用户
     */
    @Override
    public String registRcUser(String id, String name, String portrait) {
        // todo读取数据库配置
        String appKey = "kj7swf8ok3ru2";
        String appSecret = "C7b6AmxpIIFp";

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        io.rong.methods.user.User rcUser = rongCloud.user;
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setName(name);
        userModel.setPortrait(portrait);

        String rcToken = null;
        try {
            TokenResult result = rcUser.register(userModel);
            log.debug("rongCloud register ret " + result.toString());
            if (result.getCode() == 200) {
                rcToken = result.getToken();
            } else {
                log.error("rongCloud register fail code " + result.getCode());
                CommonError.CommonErr(new BaseErrResult("-1", "rongCloud register fail code " + result.getCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("rongCloud register err ", e);
            CommonError.CommonErr(new BaseErrResult("-1", e.getMessage()));
        }

        return rcToken;
    }

    /**
     * 发送通知消息
     */
    @Override
    public BaseResult sendNoticeMsg(String[] targetIds, String title, String content) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);

        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("createTime", System.currentTimeMillis() + "");
        TxtMessage msg = new TxtMessage("private", JsonObjUtils.obj2json(map));

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setSenderId("3");// 官方客服，“2”评论消息，“3”通知消息
        privateMessage.setTargetId(targetIds);
        privateMessage.setObjectName(msg.getType());
        privateMessage.setContent(msg);// msg中的json内容将通过extra自动传给终端
//        privateMessage.setPushContent("");
//        privateMessage.setPushData("{\"pushData\":\"hello\"}");
//        privateMessage.setCount("4");
//        privateMessage.setVerifyBlacklist(0);
//        privateMessage.setIsPersisted(0);
//        privateMessage.setIsCounted(0);
//        privateMessage.setIsIncludeSender(0);

        Private rcPrivate = rongCloud.message.msgPrivate;
        ResponseResult privateResult = rcPrivate.send(privateMessage);
        log.debug("private msg result " + privateResult.toString());

        if (privateResult.getCode() == 200) {
            return new BaseResult();
        } else {
            return new BaseResult("0", "失败", privateResult.getErrorMessage());
        }
    }

    /**
     * 发送评论消息
     */
    @Override
    public BaseResult sendCommentMsg(String[] targetIds, String title, String content,
                                     String commentatorId, String commentatorName,
                                     String commentatorImage) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);

        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("commentatorId", commentatorId);
        map.put("commentatorName", commentatorName);
        map.put("commentatorImage", commentatorImage);
        map.put("createTime", System.currentTimeMillis() + "");
        TxtMessage msg = new TxtMessage("private", JsonObjUtils.obj2json(map));

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setSenderId("2");// 官方客服，“2”评论消息，“3”通知消息
        privateMessage.setTargetId(targetIds);
        privateMessage.setObjectName(msg.getType());
        privateMessage.setContent(msg);// msg中的json内容将通过extra自动传给终端
//        privateMessage.setPushContent("");
//        privateMessage.setPushData("{\"pushData\":\"hello\"}");
//        privateMessage.setCount("4");
//        privateMessage.setVerifyBlacklist(0);
//        privateMessage.setIsPersisted(0);
//        privateMessage.setIsCounted(0);
//        privateMessage.setIsIncludeSender(0);

        Private rcPrivate = rongCloud.message.msgPrivate;
        ResponseResult privateResult = rcPrivate.send(privateMessage);
        log.debug("private msg result " + privateResult.toString());

        if (privateResult.getCode() == 200) {
            return new BaseResult();
        } else {
            return new BaseResult("0", "失败", privateResult.getErrorMessage());
        }
    }

    /**
     * 发送系统消息
     * senderId：“2”评论消息，“3”通知消息
     */
    @Override
    public BaseResult sendSystemMsg(String[] targetIds, String senderId, String title, String content) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);

        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        // 这里的类型InfoNtfMessage决定终端收到的objectName类型是RC:InfoNtf
        InfoNtfMessage msg = new InfoNtfMessage("system", JsonObjUtils.obj2json(map));

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setSenderId(senderId);
        systemMessage.setTargetId(targetIds);
        systemMessage.setObjectName(msg.getType());
        systemMessage.setContent(msg);// msg中的json内容将通过extra自动传给终端
//        privateMessage.setPushContent("");
//        privateMessage.setPushData("{\"pushData\":\"hello\"}");
//        privateMessage.setCount("4");
//        privateMessage.setVerifyBlacklist(0);
//        privateMessage.setIsPersisted(0);
//        privateMessage.setIsCounted(0);
//        privateMessage.setIsIncludeSender(0);

        MsgSystem system = rongCloud.message.system;
        ResponseResult privateResult = system.send(systemMessage);
        log.debug("private msg result " + privateResult.toString());

        if (privateResult.getCode() == 200) {
            return new BaseResult();
        } else {
            return new BaseResult("0", "失败", privateResult.getErrorMessage());
        }
    }

}
