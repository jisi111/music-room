package com.chuangxin.musicroom.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuangxin.musicroom.core.dto.RegionTreeNode;
import com.chuangxin.musicroom.core.entity.Region;
import com.chuangxin.musicroom.core.dto.BasePageResult;
import com.chuangxin.musicroom.core.dto.BasePage;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.client.mapper.RegionMapper;
import com.chuangxin.musicroom.client.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
@Service
@Transactional
@Slf4j
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
    
    @Autowired
    RegionMapper regionMapper;

    @Override
    public Region getByCode(String code) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        Region region = regionMapper.selectOne(queryWrapper);
        return region;
    }

    @Override
    public List<RegionTreeNode> getTree() {
        List<RegionTreeNode> topList = regionMapper.getListByLevel(1);
        for (RegionTreeNode topItem : topList) {
            List<RegionTreeNode> midList = regionMapper.getListByPcode(topItem.getId());
            for (RegionTreeNode midItem : midList) {
                List<RegionTreeNode> bottomList = regionMapper.getListByPcode(midItem.getId());
                midItem.setSub(bottomList);
            }
            topItem.setSub(midList);
        }
        return topList;
    }

    @Override
    public BaseResult getList(Page<Region> page, Map<String, Object> map) {
        page.setTotal(0);
        IPage<Region> iPage = regionMapper.getList(page, map); 
        BasePageResult pageResult = new BasePageResult(
                new BasePage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages()), 
                iPage.getRecords());
        return new BaseResult(pageResult);
    }
     
}
