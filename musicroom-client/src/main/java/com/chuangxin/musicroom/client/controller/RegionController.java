package com.chuangxin.musicroom.client.controller;

import com.chuangxin.musicroom.core.dto.RegionTreeNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.chuangxin.musicroom.client.session.SessionService;
import com.chuangxin.musicroom.client.service.RegionService;
import com.chuangxin.musicroom.core.entity.Region;
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
@Api(tags = "RegionController", description = "")
@RestController
@Slf4j
@RequestMapping("/region")
public class RegionController {
    
    @Autowired
    SessionService sessionService;
    @Autowired
    RegionService regionService;

    /**
     * 查询详情
     */
    @ApiOperation(value="查询详情", notes="")
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public BaseResult<Region> get(@RequestBody Integer id) {
        Region region = regionService.getById(id); 
        return new BaseResult("1", "成功", region);
    }

    /**
     * 查询树，字段为name，id，sub
     */
    @ApiOperation(value="查询树", notes="")
    @RequestMapping(value="/getTree", method={RequestMethod.POST})
    public List<RegionTreeNode> getTree() {
        List<RegionTreeNode> list = regionService.getTree();
        return list;
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value="查询", notes="")
    @RequestMapping(value="/getList", method={RequestMethod.POST})
    public BaseResult<BasePageResult<Region>> getList(@RequestBody Map<String, Object> param) throws Exception {
        Page<Region> page = new Page<Region>(1, 10);
        if (param.get("page") != null) {
            page = JsonObjUtils.map2obj((Map<String, Object>) param.get("page"), Page.class);
        } 
        Map<String, Object> queryParams = new HashMap<>();
        if (param.get("queryParams") != null) {
            queryParams = (Map<String, Object>) param.get("queryParams");
        }        
        return regionService.getList(page, queryParams); 
    }
    
}
