package com.chuangxin.musicroom.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuangxin.musicroom.client.mapper.RegionMapper;
import com.chuangxin.musicroom.client.service.*;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.core.dto.*;
import com.chuangxin.musicroom.core.entity.*;
import com.chuangxin.musicroom.client.mapper.UserMapper;
import com.chuangxin.musicroom.core.util.EncryptUtils;
import com.chuangxin.musicroom.core.redis.RedisParam;
import com.chuangxin.musicroom.core.util.JsonObjUtils;
import com.chuangxin.musicroom.core.util.StringUtil;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;

/**
 * @author DaiDong
 * @since 2019-08-14
 * @email 755144556@qq.com
 */
@Service
//@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    HttpServletRequest request;
    @Autowired
    SessionService sessionService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    SmsSendService smsService;
    @Autowired
    RegionService regionService;
    @Autowired
    RongCloudService rongCloudService;
    @Autowired
    PlanService planService;

    @Value("${cfg.defaultUserImg}")
    String defaultUserImg;

    @Override
    public BaseResult<User> getCheckCode(String phone) throws Exception {
        String checkCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        redisTemplate.opsForValue().set(RedisParam.CHECK_CODE + phone, checkCode, 30, TimeUnit.MINUTES);
        // 发送短信
        BaseResult result = smsService.send(phone, checkCode, 1);
        return new BaseResult(checkCode);// todo取消返回验证码
    }

    /**
     * 手机登录
     * 用户信息中的关注、粉丝、获赞、收藏数不是统计出来，而是每个业务执行时，直接加减
     */
    @Override
    public BaseResult<User> phoneLogin(LoginParam loginParam) {
        // 检查验证码
        String cacheCheckCode = redisTemplate.opsForValue().get(RedisParam.CHECK_CODE + loginParam.getPhone());
        if (cacheCheckCode == null || !cacheCheckCode.equals(loginParam.getCheckCode())) {
            return new BaseResult<>("0", "验证码错误", null);
        }

        User user = getOrAddUser(loginParam);

        // 缓存token
        String token = request.getSession().getId();
        user.setToken(token);
        redisTemplate.opsForValue().set(RedisParam.USER_INFO + token, JSON.toJSONString(user), 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisParam.USER_ID + token, user.getId() + "", 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisParam.USER_TOKEN + user.getId(), token, 2, TimeUnit.DAYS);

        // 过滤敏感数据
        user.setUserName(null);
        user.setPassword(null);
        user.setWxOpenId(null);
        if (StringUtil.isNotBlank(user.getAlipayAccount())) {
            user.setAlipayAccount(user.getAlipayAccount().substring(0, 2) + "****");
        }
        user.setAlipayRealName(null);

        return new BaseResult(user);
    }

    @Override
    public BaseResult<User> wxLogin(WxUserInfo wxUserInfo) {
        // 查询用户
        User user = getOrAddWxUser(wxUserInfo);

        // 缓存token
        String token = request.getSession().getId();
        user.setToken(token);
        redisTemplate.opsForValue().set(RedisParam.USER_INFO + token, JSON.toJSONString(user), 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisParam.USER_ID + token, user.getId() + "", 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisParam.USER_TOKEN + user.getId(), token, 2, TimeUnit.DAYS);

        // 过滤敏感数据
        user.setUserName(null);
        user.setPassword(null);
        user.setWxOpenId(null);

        return new BaseResult(user);
    }

    @Override
    public BaseResult updateImage(String url) {
        User user = sessionService.getUserInfo();
        user.setImage(url);
        this.updateById(user);
        return new BaseResult();
    }

    @Synchronized
    private User getOrAddUser(LoginParam loginParam) {
        // 根据手机号查询用户
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("user_name", loginParam.getPhone());
        userQuery.eq("is_valid", 1);
        User user = userMapper.selectOne(userQuery);

        // 没有该用户，注册
        if (user == null) {
            user = new User();
            user.setUserName(loginParam.getPhone());
            String nickName = "享啦用户" + loginParam.getPhone().substring(7, 11);
            user.setNickName(nickName);
            user.setPhone(loginParam.getPhone());
            user.setAccount(0F);
            user.setImage(defaultUserImg);
            // user.setSign("请写上您的个性签名");
            user.setContactPhone(loginParam.getPhone());
            user.setContactName(nickName);
            user.setIsValid(1);

            // 省市区
            if (loginParam.getDistrictCode() != null) {
                Region district = regionService.getByCode(loginParam.getDistrictCode());
                Region city = regionService.getByCode(district.getPcode());
                Region province = regionService.getByCode(city.getPcode());
                user.setDistrictId(district.getId());
                user.setDistrictName(district.getName());
                user.setCityId(city.getId());
                user.setCityName(city.getName());
                user.setProvinceId(province.getId());
                user.setProvinceName(province.getName());
            }
            userMapper.insertReturnId(user);
            log.debug(user.toString());
        } else {
            // 查询当前plan
//            BaseResult planResult = planService.getCurrentPlan();
//            Plan plan = (Plan) planResult.getData();
//            user.setCurrentPlan(plan);
//
//            // 查询当前plan中blog数量
//            if (plan != null) {
//                Integer blogNum = planService.getBlogNum(plan.getId());
//                user.setPlanBlogNum(blogNum);
//            }
        }

        // 如果没有注册融云，注册
        if (StringUtil.isBlank(user.getRcToken())) {
            String rcToken = rongCloudService.registRcUser(user.getId() + "",
                    user.getNickName(), user.getImage());
            if (rcToken != null) {
                user.setRcToken(rcToken);
                userMapper.updateById(user);
            }
        }

        // 过滤敏感数据
        user.setUserName(null);
        user.setPassword(null);
        return user;
    }

    @Synchronized
    private User getOrAddWxUser(WxUserInfo wxUserInfo) {
        // 根据openId查询用户
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("wx_open_id", wxUserInfo.getOpenid());
        userQuery.eq("is_valid", 1);
        User user = userMapper.selectOne(userQuery);

        // 没有该用户，注册
        if (user == null) {
            user = new User();
            user.setWxOpenId(wxUserInfo.getOpenid());
            user.setNickName(wxUserInfo.getNickname());
            user.setImage(wxUserInfo.getHeadimgurl());
            user.setSex(wxUserInfo.getSex());
            user.setCityName(wxUserInfo.getCity());
            user.setProvinceName(wxUserInfo.getProvince());
            user.setIsValid(1);
            userMapper.insertReturnId(user);
            log.debug(user.toString());
        }

        // 如果没有注册融云，注册
        if (StringUtil.isBlank(user.getRcToken())) {
            String rcToken = rongCloudService.registRcUser(user.getId() + "",
                    user.getNickName(), user.getImage());
            if (rcToken != null) {
                user.setRcToken(rcToken);
                userMapper.updateById(user);
            }
        }

        return user;
    }

    @Override
    public BaseResult<User> accountLogin(LoginParam loginParam) {
        // 查询用户
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("user_name", loginParam.getPhone());
        userQuery.eq("is_valid", 1);
        User user = userMapper.selectOne(userQuery);
        if (user == null) {
            return new BaseResult<>("0", "没有该账号", null);
        }

        // 查询密码
        String encodePassword = EncryptUtils.encodeMD5String(EncryptUtils.encodeBase64String(loginParam.getPassword()));
        log.debug("encodePassword " + encodePassword);
        if (!encodePassword.equals(user.getPassword())) {
            return new BaseResult<>("0", "密码错误", null);
        }

        // 缓存token
        String token = request.getSession().getId();
        user.setToken(token);
        redisTemplate.opsForValue().set(RedisParam.USER_INFO + token, JSON.toJSONString(user), 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisParam.USER_ID + token, user.getId() + "", 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisParam.USER_TOKEN + user.getId(), token, 2, TimeUnit.DAYS);

        // 过滤敏感数据
        user.setUserName(null);
        user.setPassword(null);

        return new BaseResult(user);
    }

    @Override
    public BaseResult update(User user) {
        log.debug("user " + user.toString());
        if (!user.getId().equals(sessionService.getUserId())) {
            return new BaseResult<>("0", "非本人", null);
        }

        // 不允许修改资金、关注数、粉丝数等
        user.setAccount(null);
        user.setFanNum(null);
        user.setFollowNum(null);
        user.setAuthLevel(null);
        user.setPassword(null);
        user.setIsValid(null);
        user.setLikesNum(null);
        user.setBadgeLevel(null);
        user.setCollectNum(null);
        user.setUserName(null);
        user.setAlipayAccount(null);
        user.setAlipayRealName(null);
        userMapper.updateById(user);
        return new BaseResult("1", "成功", user);
    }

    @Override
    public BaseResult logout() {
        // 清空token
        String token = request.getHeader("token");
        redisTemplate.delete(redisTemplate.keys(RedisParam.USER_INFO + token));
        redisTemplate.delete(redisTemplate.keys(RedisParam.USER_ID + token));

        try {// 防止sessionService.getUserId()失效exception时，直接返回了，不能退出
            Integer userId = sessionService.getUserId();
            redisTemplate.delete(redisTemplate.keys(RedisParam.USER_TOKEN + userId));
        } catch (Exception e) {
        }

        return new BaseResult();
    }

    /**
     * 绑定支付宝，获取验证码
     */
    @Override
    public BaseResult bindAlipayCheckCode(Map<String, Object> param) throws Exception {
        if (param.get("phone") == null || param.get("alipayAccount") == null
                || param.get("alipayRealName") == null) {
            return new BaseResult("0", "参数缺失", null);
        }
        String phone = (String) param.get("phone");
        String alipayAccount = (String) param.get("alipayAccount");
        String alipayRealName = (String) param.get("alipayRealName");
        if (StringUtil.isBlank(phone) || StringUtil.isBlank(alipayAccount) || StringUtil.isBlank(alipayRealName)) {
            return new BaseResult("0", "参数缺失", null);
        }

        // 验证码
        String checkCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        param.put("checkCode", checkCode);

        // 发送短信
        BaseResult result = smsService.send(phone, checkCode, 1);

        redisTemplate.opsForValue().set(RedisParam.BIND_ALIPAY_ + phone, JSON.toJSONString(param), 30, TimeUnit.MINUTES);
        return new BaseResult(checkCode);// todo取消返回验证码
    }

    /**
     * 绑定支付宝
     */
    @Override
    public BaseResult bindAlipay(Map<String, Object> param) throws Exception {
        if (param.get("phone") == null || param.get("checkCode") == null) {
            return new BaseResult("0", "参数缺失", null);
        }
        String phone = (String) param.get("phone");
        String checkCode = (String) param.get("checkCode");

        // 检查验证码
        String bindInfo = redisTemplate.opsForValue().get(RedisParam.BIND_ALIPAY_ + phone);
        if (StringUtil.isBlank(bindInfo)) {
            return new BaseResult("0", "验证为空", null);
        }
        Map<String, Object> map = JsonObjUtils.str2obj(bindInfo, Map.class);
        String cacheCheckCode = (String) map.get("checkCode");
        if (!cacheCheckCode.equals(checkCode)) {
            return new BaseResult("0", "验证失败", null);
        }

        // 检查是否为登录用户手机号
        Integer userId = sessionService.getUserId();
        if (userId == null) {
            return new BaseResult("0", "请登录", null);
        }
        User user = sessionService.getUserInfo();
        if (StringUtil.isBlank(user.getPhone())) {
            return new BaseResult("0", "请先在用户资料中填入手机", null);
        }
        if (!(user.getPhone()).equals(phone)) {
            return new BaseResult("0", "与用户资料中手机不一致", null);
        }

        if (StringUtil.isNotBlank(user.getAlipayAccount())) {
            return new BaseResult("0", "已绑定过账号，不能重复绑定", null);
        }

        // 绑定
        String alipayAccount = (String) map.get("alipayAccount");
        String alipayRealName = (String) map.get("alipayRealName");
        user.setAlipayAccount(alipayAccount);
        user.setAlipayRealName(alipayRealName);
        userMapper.updateById(user);

        user.setAlipayAccount(alipayAccount.substring(0, 4) + "****" + alipayAccount.substring(8, alipayAccount.length()));
        return new BaseResult(user);
    }

    @Override
    public BaseResult<User> changePassword(LoginParam loginParam) {
        // 检查验证码

        // 根据手机号查询用户
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("user_name", loginParam.getPhone());
        userQuery.eq("is_valid", 1);
        User user = userMapper.selectOne(userQuery);
        if (user == null) {
            return new BaseResult<>("0", "没有该账号", null);
        }

        String password = EncryptUtils.encodeMD5String(EncryptUtils.encodeBase64String(loginParam.getPassword()));
        user.setPassword(password);
        userMapper.updateById(user);

        return new BaseResult();
    }

    @Override
    public BaseResult getList(Page<User> page, Map<String, Object> map) {
        page.setTotal(0);
        IPage<User> iPage = userMapper.getList(page, map); 
        BasePageResult pageResult = new BasePageResult(
                new BasePage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages()),
                iPage.getRecords());
        return new BaseResult(pageResult);
    }

    /**
     *  查询用户简单信息
     */
    @Override
    public User getSimpleInfo(Integer id) {
        User user = userMapper.selectById(id);
        User simpleInfo = new User();
        if (user != null) {
            simpleInfo.setId(id);
            simpleInfo.setNickName(user.getNickName());
            simpleInfo.setImage(user.getImage());
            simpleInfo.setProvinceName(user.getProvinceName());
            simpleInfo.setCityName(user.getCityName());
            return simpleInfo;
        } else {
            return null;
        }
    }

    /**
     *  查询用户基本信息，不含敏感信息
     */
    @Override
    public User getInfo(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return null;
        }
        User simpleInfo = new User();
        simpleInfo.setId(id);
        simpleInfo.setNickName(user.getNickName());
        simpleInfo.setContactName(user.getContactName());
        simpleInfo.setImage(user.getImage());
        simpleInfo.setProvinceName(user.getProvinceName());
        simpleInfo.setCityName(user.getCityName());
        simpleInfo.setAuthLevel(user.getAuthLevel());
        simpleInfo.setSign(user.getSign());
        simpleInfo.setSex(user.getSex());
        simpleInfo.setFollowNum(user.getFollowNum());
        simpleInfo.setCollectNum(user.getCollectNum());
        simpleInfo.setFanNum(user.getFanNum());
        simpleInfo.setLikesNum(user.getLikesNum());
        simpleInfo.setBirthday(user.getBirthday());

//        // 当前登录用户是否已关注该用户
//        QueryWrapper<Follow> followWrapper = new QueryWrapper<>();
//        followWrapper.eq("user_id", user.getId());
//        followWrapper.eq("fan_id", sessionService.getUserId());
//        followWrapper.eq("is_valid", 1);
//        Follow follow = followService.getOne(followWrapper);
//        if (follow == null) {
//            simpleInfo.setIsFollowed(0);
//        } else {
//            simpleInfo.setIsFollowed(1);
//        }

        return simpleInfo;
    }

    @Override
    public BaseResult updateContact(User user) {
        User newUser = userMapper.selectById(sessionService.getUserId());
        newUser.setContactName(user.getContactName());
        newUser.setContactPhone(user.getContactPhone());
        newUser.setContactAddress(user.getContactAddress());

        // 省市区
        // 省市区
        if (user.getDistrictCode() != null) {
            Region district = regionService.getByCode(user.getDistrictCode());
            Region city = regionService.getByCode(district.getPcode());
            Region province = regionService.getByCode(city.getPcode());
            user.setDistrictId(district.getId());
            user.setDistrictName(district.getShortName());
            user.setCityId(city.getId());
            user.setCityName(city.getShortName());
            user.setProvinceId(province.getId());
            user.setProvinceName(province.getShortName());
        }

        userMapper.updateById(user);
        return new BaseResult("1", "成功", null);
    }
}
