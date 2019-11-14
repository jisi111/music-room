package com.chuangxin.musicroom.client.controller;

import com.chuangxin.musicroom.client.service.RegionService;
import com.chuangxin.musicroom.core.dto.LoginParam;
import com.chuangxin.musicroom.core.dto.WxUserInfo;
import com.chuangxin.musicroom.core.entity.Plan;
import com.chuangxin.musicroom.core.entity.Region;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.client.service.UserService;
import com.chuangxin.musicroom.core.entity.User;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.dto.BasePageResult;
import com.chuangxin.musicroom.core.util.JsonObjUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
@Api(tags = "UserController", description = "")
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;
    @Autowired
    RegionService regionService;

    /**
     * 获取验证码
     */
    @ApiOperation(value="获取验证码", notes="")
    @RequestMapping(value="/getCheckCode", method={RequestMethod.POST})
    public BaseResult<User> getCheckCode(@RequestBody String phone) throws Exception {
        return userService.getCheckCode(phone);
    }

    /**
     * 手机登录
     */
    @ApiOperation(value="手机登录", notes="")
    @RequestMapping(value="/phoneLogin", method={RequestMethod.POST})
    public BaseResult<User> phoneLogin(@RequestBody LoginParam loginParam) throws Exception {
        return userService.phoneLogin(loginParam);
    }

    /**
     * 更换头像
     */
    @ApiOperation(value="更换头像", notes="")
    @RequestMapping(value="/updateImage", method={RequestMethod.POST})
    public BaseResult<User> updateImage(@RequestBody String url) throws Exception {
        return userService.updateImage(url);
    }

    /**
     * 绑定支付宝，获取验证码
     */
    @ApiOperation(value="绑定支付宝，获取验证码", notes="")
    @RequestMapping(value="/bindAlipayCheckCode", method={RequestMethod.POST})
    public BaseResult<User> bindAlipayCheckCode(@RequestBody Map<String, Object> param) throws Exception {
        return userService.bindAlipayCheckCode(param);
    }

    /**
     * 绑定支付宝
     */
    @ApiOperation(value="绑定支付宝", notes="")
    @RequestMapping(value="/bindAlipay", method={RequestMethod.POST})
    public BaseResult bindAlipay(@RequestBody Map<String, Object> param) throws Exception {
        return userService.bindAlipay(param);
    }

    /**
     * 微信登录
     */
    @ApiOperation(value="微信登录", notes="")
    @RequestMapping(value="/wxLogin", method={RequestMethod.POST})
    public BaseResult<User> wxLogin(@RequestBody WxUserInfo wxUserInfo) throws Exception {
        return userService.wxLogin(wxUserInfo);
    }

    /**
     * 账号密码登录
     */
    @ApiOperation(value="账号密码登录", notes="")
    @RequestMapping(value="/accountLogin", method={RequestMethod.POST})
    public BaseResult<User> accountLogin(@RequestBody LoginParam loginParam) throws Exception {
        return userService.accountLogin(loginParam);
    }

    /**
     * 退出
     */
    @ApiOperation(value="退出", notes="")
    @RequestMapping(value="/logout", method={RequestMethod.POST})
    public BaseResult logout() throws Exception {
        return userService.logout();
    }

    /**
     * 修改密码
     */
    @ApiOperation(value="修改密码", notes="")
    @RequestMapping(value="/changePassword", method={RequestMethod.POST})
    public BaseResult<User> changePassword(@RequestBody LoginParam loginParam) throws Exception {
        return userService.changePassword(loginParam);
    }

    /**
     * 修改
     */
    @ApiOperation(value="修改", notes="")
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public BaseResult<User> update(@RequestBody User user) throws Exception {
        return userService.update(user);
    }

    /**
     * 修改联系资料
     */
    @ApiOperation(value="修改联系资料", notes="")
    @RequestMapping(value="/updateContact", method={RequestMethod.POST})
    public BaseResult<User> updateContact(@RequestBody User user) throws Exception {
        return userService.updateContact(user);
    }

    /**
     * 删除
     */
    @ApiOperation(value="删除", notes="")
    @RequestMapping(value="/delete", method={RequestMethod.POST})
    public BaseResult delete(@RequestBody Integer id) {
        User user = new User();
        user.setId(id); 
        user.setIsValid(0);
        user.setUpdateUserId(sessionService.getUserId());
        userService.updateById(user);
        return new BaseResult();
    }
    
    /**
     * 批量删除
     */
    @ApiOperation(value="批量删除", notes="")
    @RequestMapping(value="/batchDelete", method={RequestMethod.POST})
    public BaseResult batchDelete(@RequestBody Integer[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            return new BaseResult();
        }
        List<User> userList = new ArrayList<>();
        for (Integer id : ids) {
            User user = new User();
            user.setId(id);
            user.setIsValid(0);
            userList.add(user);
        }
        userService.updateBatchById(userList);
        return new BaseResult();
    }

    /**
     * 查询详情，不会返回敏感字段
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<User> get(@RequestBody Integer id) {
        User user = userService.getInfo(id);
        return new BaseResult("1", "成功", user);
    }

    /**
     * 查询登录用户详情，返回全部敏感字段，查询预约排位
     */
    @ApiOperation(value="查询登录用户详情", notes="")
    @RequestMapping(value="/getDetail", method={RequestMethod.POST})
    public BaseResult<User> getDetail() throws Exception {
        User user = userService.getById(sessionService.getUserId());
        user.setPassword(null);

        // 查询当前plan
//        BaseResult planResult = planService.getCurrentPlan();
//        Plan plan = (Plan) planResult.getData();
//        user.setCurrentPlan(plan);

        return new BaseResult<>(user);
    }

    /**
     * 查询账户余额
     */
    @ApiOperation(value="查询账户余额", notes="")
    @RequestMapping(value="/getAccount", method={RequestMethod.POST})
    public BaseResult getAccount() {
        User user = userService.getById(sessionService.getUserId());
        return new BaseResult("1", "成功", user.getAccount());
    }

    /**
     * 查询我的资料
     */
    @ApiOperation(value="查询我的资料", notes="")
    @RequestMapping(value="/getMyInfo", method={RequestMethod.POST})
    public BaseResult<User> getMyInfo() {
        User user = userService.getInfo(sessionService.getUserId());
        return new BaseResult("1", "成功", user);
    }

    /**
     * 更新地址
     */
    @ApiOperation(value="更新地址", notes="")
    @RequestMapping(value="/updateAddress", method={RequestMethod.POST})
    public BaseResult updateAddress(@RequestBody Map<String, Object> param) {
        User user = userService.getById(sessionService.getUserId());
        user.setContactName((String) param.get("contactName"));
        user.setContactPhone((String) param.get("contactPhone"));
        user.setContactAddress((String) param.get("contactAddress"));

        Region province = regionService.getByCode((String) param.get("provinceCode"));
        Region city = regionService.getByCode((String) param.get("provinceCode"));
        Region district = regionService.getByCode((String) param.get("provinceCode"));
        user.setProvinceId(province.getId());
        user.setCityId(city.getId());
        user.setDistrictId(district.getId());
        userService.updateById(user);

        return new BaseResult();
    }

    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<User>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<User> page = new Page<User>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return userService.getList(page, queryParams); 
    }

    /**
     * 根据ids查询用户列表
     */
    @ApiOperation(value="根据ids查询用户列表", notes="")
    @RequestMapping(value="/getListByIds", method={RequestMethod.POST})
    public BaseResult<List<User>> getListByIds(@RequestBody String ids) throws Exception {
        List<User> list = new ArrayList<>();
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            String id = idArray[i];
            User user = userService.getSimpleInfo(Integer.valueOf(id));
            if (user != null) {
                user.setRcToken(null);
                list.add(user);
            }
        }
        return new BaseResult<>(list);
    }

}
