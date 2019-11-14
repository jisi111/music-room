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
import com.chuangxin.musicroom.client.service.SmsRecordService;
import com.chuangxin.musicroom.core.entity.SmsRecord;
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
@Api(tags = "SmsRecordController", description = "")
@RestController
@Slf4j
@RequestMapping("/smsRecord")
public class SmsRecordController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    SmsRecordService smsRecordService;

    /**
     * 新增
     */
    @ApiOperation(value="新增", notes="")
    @RequestMapping(value="/add", method={RequestMethod.POST})
    public BaseResult<SmsRecord> add(@RequestBody SmsRecord smsRecord) throws Exception {
        smsRecord.setIsValid(1);
        smsRecord.setCreateUserId(sessionService.getUserId());
        smsRecord.setUpdateTime(new Date());
        smsRecordService.save(smsRecord); 
        return new BaseResult();
    }
    
    /**
     * 修改
     */
    @ApiOperation(value="修改", notes="")
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public BaseResult<SmsRecord> update(@RequestBody SmsRecord smsRecord) throws Exception {
        smsRecord.setUpdateUserId(sessionService.getUserId());
        smsRecordService.updateById(smsRecord); 
        return new BaseResult("1", "成功", smsRecord);
    }

    /**
     * 删除
     */
    @ApiOperation(value="删除", notes="")
    @RequestMapping(value="/delete", method={RequestMethod.POST})
    public BaseResult delete(@RequestBody Integer id) {
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setId(id); 
        smsRecord.setIsValid(0);
        smsRecord.setUpdateUserId(sessionService.getUserId());
        smsRecordService.updateById(smsRecord);
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
        List<SmsRecord> smsRecordList = new ArrayList<>();
        for (Integer id : ids) {
            SmsRecord smsRecord = new SmsRecord();
            smsRecord.setId(id);
            smsRecord.setIsValid(0);
            smsRecordList.add(smsRecord);
        }
        smsRecordService.updateBatchById(smsRecordList);
        return new BaseResult();
    }

    /**
     * 查询详情
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<SmsRecord> get(@RequestBody Integer id) {
        SmsRecord smsRecord = smsRecordService.getById(id); 
        return new BaseResult("1", "成功", smsRecord);
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<SmsRecord>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<SmsRecord> page = new Page<SmsRecord>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return smsRecordService.getList(page, queryParams); 
    }
    
}
