package com.chuangxin.musicroom.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuangxin.musicroom.core.entity.SystemConfig;
import com.chuangxin.musicroom.client.mapper.SystemConfigMapper;
import com.chuangxin.musicroom.client.service.SystemConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
@Service
@Transactional
@Slf4j
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {
    
    @Override
    public String getByCode(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);
        SystemConfig config = this.getOne(queryWrapper);
        return config.getValue();
    }

    @Override
    public Boolean updateByCode(String code, String value) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", code);
        SystemConfig config = this.getOne(queryWrapper);
        config.setValue(value);
        return this.updateById(config);
    }
}
