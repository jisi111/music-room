package com.chuangxin.musicroom.admin.session;

import com.chuangxin.musicroom.core.dto.BaseErrResult;
import com.chuangxin.musicroom.core.entity.User;
import com.chuangxin.musicroom.core.exception.CommonError;
import com.chuangxin.musicroom.core.redis.RedisParam;
import com.chuangxin.musicroom.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class SessionService {

    @Autowired
    HttpServletRequest request;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 读取缓存的userId
     */
    public Integer getUserId() {
        // return 1;
        String token = request.getHeader("token");
        log.debug("token " + token);
        if (StringUtil.isBlank(token)) {
            return null;
            // CommonError.CommonErr(new BaseErrResult("-1", "Token为空，请登录"));
        }
        String userIdStr = null;
        try {
            userIdStr = redisTemplate.opsForValue().get(RedisParam.USER_ID + token);
        } catch (Exception e) {
            e.printStackTrace();
            CommonError.CommonErr(new BaseErrResult("-1", "Redis异常"));
        }
        if (StringUtil.isBlank(userIdStr)) {
            CommonError.CommonErr(new BaseErrResult("-1", "登录超时，请重新登录"));
        }
        return Integer.valueOf(userIdStr);
    }

    /**
     * 读取缓存的userInfo
     */
    public User getUserInfo() {
        User user = null;
        if (request.getSession().getAttribute("user") != null) {
            user = (User) request.getSession().getAttribute("user");
        } 
        return user;
    }

}
