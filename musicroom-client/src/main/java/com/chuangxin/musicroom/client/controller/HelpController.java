package com.chuangxin.musicroom.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuangxin.musicroom.core.util.RequestUtil;
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
import com.chuangxin.musicroom.client.service.HelpService;
import com.chuangxin.musicroom.core.entity.Help;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.dto.BasePageResult;
import com.chuangxin.musicroom.core.util.JsonObjUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
@Api(tags = "HelpController", description = "")
@RestController
@Slf4j
@RequestMapping("/help")
public class HelpController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    SessionService sessionService;
    @Autowired
    HelpService helpService;

    /**
     * 新增
     */
    @ApiOperation(value="新增", notes="")
    @RequestMapping(value="/add", method={RequestMethod.POST})
    public BaseResult<Help> add(@RequestBody Help help) throws Exception {
        help.setIsValid(1);
        help.setCreateUserId(sessionService.getUserId());
        help.setUpdateTime(new Date());
        helpService.save(help); 
        return new BaseResult();
    }

    /**
     * 根据code修改
     */
    @ApiOperation(value="根据code修改", notes="")
    @RequestMapping(value="/updateByCode", method={RequestMethod.POST})
    public BaseResult<Help> updateByCode(@RequestBody Help help) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", help.getCode());
        Help oldOne = helpService.getOne(queryWrapper);
        oldOne.setContent(help.getContent());
        oldOne.setUpdateUserId(sessionService.getUserId());
        helpService.updateById(oldOne);
        return new BaseResult("1", "成功", null);
    }

    /**
     * 根据code查询
     */
    @ApiOperation(value="根据code查询", notes="")
    @RequestMapping(value="/getByCode", method={RequestMethod.POST})
    public BaseResult<Help> getByCode(@RequestBody String code) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);
        Help help = helpService.getOne(queryWrapper);

        // 替换图片前缀域名
        String content = help.getContent();
        String serverDomain = RequestUtil.getBasePath(request);
        content = content.replace("http://localhost:8001", serverDomain);// 测试时存入图片地址为localhost，实时转换为服务器地址
        help.setContent(content);

        return new BaseResult("1", "成功", help);
    }

    /**
     * 修改
     */
    @ApiOperation(value="修改", notes="")
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public BaseResult<Help> update(@RequestBody Help help) throws Exception {
        help.setUpdateUserId(sessionService.getUserId());
        helpService.updateById(help); 
        return new BaseResult("1", "成功", help);
    }

    /**
     * 删除
     */
    @ApiOperation(value="删除", notes="")
    @RequestMapping(value="/delete", method={RequestMethod.POST})
    public BaseResult delete(@RequestBody Integer id) {
        Help help = new Help();
        help.setId(id); 
        help.setIsValid(0);
        help.setUpdateUserId(sessionService.getUserId());
        helpService.updateById(help);
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
        List<Help> helpList = new ArrayList<>();
        for (Integer id : ids) {
            Help help = new Help();
            help.setId(id);
            help.setIsValid(0);
            helpList.add(help);
        }
        helpService.updateBatchById(helpList);
        return new BaseResult();
    }

    /**
     * 查询详情
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<Help> get(@RequestBody Integer id) {
        Help help = helpService.getById(id); 
        return new BaseResult("1", "成功", help);
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<Help>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<Help> page = new Page<Help>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return helpService.getList(page, queryParams); 
    }
    
}
