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
import com.chuangxin.musicroom.client.service.PlanService;
import com.chuangxin.musicroom.core.entity.Plan;
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
@Api(tags = "PlanController", description = "")
@RestController
@Slf4j
@RequestMapping("/plan")
public class PlanController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    PlanService planService;

    /**
     * 新增
     */
    @ApiOperation(value="新增", notes="")
    @RequestMapping(value="/add", method={RequestMethod.POST})
    public BaseResult<Plan> add(@RequestBody Plan plan) throws Exception {
        plan.setIsValid(1);
        plan.setCreateUserId(sessionService.getUserId());
        plan.setUpdateTime(new Date());
        planService.save(plan); 
        return new BaseResult();
    }
    
    /**
     * 修改
     */
    @ApiOperation(value="修改", notes="")
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public BaseResult<Plan> update(@RequestBody Plan plan) throws Exception {
        plan.setUpdateUserId(sessionService.getUserId());
        planService.updateById(plan); 
        return new BaseResult("1", "成功", plan);
    }

    /**
     * 删除
     */
    @ApiOperation(value="删除", notes="")
    @RequestMapping(value="/delete", method={RequestMethod.POST})
    public BaseResult delete(@RequestBody Integer id) {
        Plan plan = new Plan();
        plan.setId(id); 
        plan.setIsValid(0);
        plan.setUpdateUserId(sessionService.getUserId());
        planService.updateById(plan);
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
        List<Plan> planList = new ArrayList<>();
        for (Integer id : ids) {
            Plan plan = new Plan();
            plan.setId(id);
            plan.setIsValid(0);
            planList.add(plan);
        }
        planService.updateBatchById(planList);
        return new BaseResult();
    }

    /**
     * 查询详情
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<Plan> get(@RequestBody Integer id) {
        Plan plan = planService.getById(id); 
        return new BaseResult("1", "成功", plan);
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<Plan>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<Plan> page = new Page<Plan>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return planService.getList(page, queryParams); 
    }
    
}
