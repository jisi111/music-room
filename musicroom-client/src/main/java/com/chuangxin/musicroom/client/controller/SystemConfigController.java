package com.chuangxin.musicroom.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.client.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
@Api(tags = "SystemConfigController", description = "")
@RestController
@Slf4j
@RequestMapping("/systemConfig")
public class SystemConfigController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    SystemConfigService systemConfigService;

    /**
     * 新增
     */
//    @ApiOperation(value="新增", notes="")
//    @RequestMapping(value="/add", method={RequestMethod.POST})
//    public BaseResult<SystemConfig> add(@RequestBody SystemConfig systemConfig) throws Exception {
//        systemConfig.setIsValid(1);
//        systemConfig.setCreateUserId(sessionService.getUserId());
//        systemConfig.setUpdateTime(new Date());
//        systemConfigService.save(systemConfig);
//        return new BaseResult();
//    }
    
    /**
     * 修改
     */
//    @ApiOperation(value="修改", notes="")
//    @RequestMapping(value="/update", method={RequestMethod.POST})
//    public BaseResult<SystemConfig> update(@RequestBody SystemConfig systemConfig) throws Exception {
//        systemConfig.setUpdateUserId(sessionService.getUserId());
//        systemConfigService.updateById(systemConfig);
//        return new BaseResult("1", "成功", systemConfig);
//    }

    /**
     * 删除
     */
//    @ApiOperation(value="删除", notes="")
//    @RequestMapping(value="/delete", method={RequestMethod.POST})
//    public BaseResult delete(@RequestBody Integer id) {
//        SystemConfig systemConfig = new SystemConfig();
//        systemConfig.setId(id);
//        systemConfig.setIsValid(0);
//        systemConfig.setUpdateUserId(sessionService.getUserId());
//        systemConfigService.updateById(systemConfig);
//        return new BaseResult();
//    }
    
    /**
     * 批量删除
     */
//    @ApiOperation(value="批量删除", notes="")
//    @RequestMapping(value="/batchDelete", method={RequestMethod.POST})
//    public BaseResult batchDelete(@RequestBody Integer[] ids) throws Exception {
//        if (ids == null || ids.length == 0) {
//            return new BaseResult();
//        }
//        List<SystemConfig> systemConfigList = new ArrayList<>();
//        for (Integer id : ids) {
//            SystemConfig systemConfig = new SystemConfig();
//            systemConfig.setId(id);
//            systemConfig.setIsValid(0);
//            systemConfigList.add(systemConfig);
//        }
//        systemConfigService.updateBatchById(systemConfigList);
//        return new BaseResult();
//    }

    /**
     * 查询详情
     */
//    @ApiOperation(value="查询详情", notes="")
//    @RequestMapping(value="/get", method={RequestMethod.POST})
//    public BaseResult<SystemConfig> get(@RequestBody Integer id) {
//        SystemConfig systemConfig = systemConfigService.getById(id);
//        return new BaseResult("1", "成功", systemConfig);
//    }
    
    /**
     * 分页查询
     */
//    @ApiOperation(value="查询", notes="")
//    @RequestMapping(value="/getList", method={RequestMethod.POST})
//    public BaseResult<BasePageResult<SystemConfig>> getList(@RequestBody Map<String, Object> param) throws Exception {
//        Page<SystemConfig> page = new Page<SystemConfig>(1, 10);
//        if (param.get("page") != null) {
//            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
//        }
//        Map<String, Object> queryParams = new HashMap<>();
//        if (param.get("queryParams") != null) {
//            queryParams = (Map<String, Object>) param.get("queryParams");
//        }
//        return systemConfigService.getList(page, queryParams);
//    }
    
}
