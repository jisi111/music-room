package com.chuangxin.musicroom.client.service.impl;

import com.chuangxin.musicroom.core.entity.Plan;
import com.chuangxin.musicroom.core.dto.BasePageResult;
import com.chuangxin.musicroom.core.dto.BasePage;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.client.mapper.PlanMapper;
import com.chuangxin.musicroom.client.service.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
    
    @Autowired
    PlanMapper planMapper;
    
    @Override
    public BaseResult getList(Page<Plan> page, Map<String, Object> map) {
        page.setTotal(0);
        IPage<Plan> iPage = planMapper.getList(page, map); 
        BasePageResult pageResult = new BasePageResult(
                new BasePage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages()), 
                iPage.getRecords());
        return new BaseResult(pageResult);
    }
     
}
