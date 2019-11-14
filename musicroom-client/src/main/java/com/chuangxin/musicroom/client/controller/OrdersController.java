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
import com.chuangxin.musicroom.client.service.OrdersService;
import com.chuangxin.musicroom.core.entity.Orders;
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
@Api(tags = "OrdersController", description = "")
@RestController
@Slf4j
@RequestMapping("/orders")
public class OrdersController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    OrdersService ordersService;

    /**
     * 新增
     */
    @ApiOperation(value="新增", notes="")
    @RequestMapping(value="/add", method={RequestMethod.POST})
    public BaseResult<Orders> add(@RequestBody Orders orders) throws Exception {
        orders.setIsValid(1);
        orders.setCreateUserId(sessionService.getUserId());
        orders.setUpdateTime(new Date());
        ordersService.save(orders); 
        return new BaseResult();
    }
    
    /**
     * 修改
     */
    @ApiOperation(value="修改", notes="")
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public BaseResult<Orders> update(@RequestBody Orders orders) throws Exception {
        orders.setUpdateUserId(sessionService.getUserId());
        ordersService.updateById(orders); 
        return new BaseResult("1", "成功", orders);
    }

    /**
     * 删除
     */
    @ApiOperation(value="删除", notes="")
    @RequestMapping(value="/delete", method={RequestMethod.POST})
    public BaseResult delete(@RequestBody String id) {
        Orders orders = new Orders();
        orders.setId(id); 
        orders.setIsValid(0);
        orders.setUpdateUserId(sessionService.getUserId());
        ordersService.updateById(orders);
        return new BaseResult();
    }
    
    /**
     * 批量删除
     */
    @ApiOperation(value="批量删除", notes="")
    @RequestMapping(value="/batchDelete", method={RequestMethod.POST})
    public BaseResult batchDelete(@RequestBody String[] ids) throws Exception {
        if (ids == null || ids.length == 0) {
            return new BaseResult();
        }
        List<Orders> ordersList = new ArrayList<>();
        for (String id : ids) {
            Orders orders = new Orders();
            orders.setId(id);
            orders.setIsValid(0);
            ordersList.add(orders);
        }
        ordersService.updateBatchById(ordersList);
        return new BaseResult();
    }

    /**
     * 查询详情
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<Orders> get(@RequestBody Integer id) {
        Orders orders = ordersService.getById(id); 
        return new BaseResult("1", "成功", orders);
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<Orders>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<Orders> page = new Page<Orders>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return ordersService.getList(page, queryParams); 
    }
    
}
