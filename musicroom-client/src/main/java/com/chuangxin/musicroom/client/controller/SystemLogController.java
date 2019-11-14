package com.chuangxin.musicroom.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.client.service.SystemLogService;
import com.chuangxin.musicroom.core.entity.SystemLog;
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
@Api(tags = "SystemLogController", description = "")
@RestController
@Slf4j
@RequestMapping("/systemLog")
public class SystemLogController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    SystemLogService systemLogService;

    /**
     * 新增
     */
    @ApiOperation(value="新增", notes="")
    @RequestMapping(value="/add", method={RequestMethod.POST})
    public BaseResult<SystemLog> add(@RequestBody SystemLog systemLog) throws Exception {
        systemLog.setIsValid(1);
        systemLog.setCreateUserId(sessionService.getUserId());
        systemLog.setUpdateTime(new Date());
        systemLogService.save(systemLog); 
        return new BaseResult();
    }
    
    /**
     * 修改
     */
    @ApiOperation(value="修改", notes="")
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public BaseResult<SystemLog> update(@RequestBody SystemLog systemLog) throws Exception {
        systemLog.setUpdateUserId(sessionService.getUserId());
        systemLogService.updateById(systemLog); 
        return new BaseResult("1", "成功", systemLog);
    }

    /**
     * 删除
     */
    @ApiOperation(value="删除", notes="")
    @RequestMapping(value="/delete", method={RequestMethod.POST})
    public BaseResult delete(@RequestBody Integer id) {
        SystemLog systemLog = new SystemLog();
        systemLog.setId(id); 
        systemLog.setIsValid(0);
        systemLog.setUpdateUserId(sessionService.getUserId());
        systemLogService.updateById(systemLog);
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
        List<SystemLog> systemLogList = new ArrayList<>();
        for (Integer id : ids) {
            SystemLog systemLog = new SystemLog();
            systemLog.setId(id);
            systemLog.setIsValid(0);
            systemLogList.add(systemLog);
        }
        systemLogService.updateBatchById(systemLogList);
        return new BaseResult();
    }

    /**
     * 查询详情
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<SystemLog> get(@RequestBody Integer id) {
        SystemLog systemLog = systemLogService.getById(id); 
        return new BaseResult("1", "成功", systemLog);
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<SystemLog>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<SystemLog> page = new Page<SystemLog>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return systemLogService.getList(page, queryParams); 
    }
    
}
