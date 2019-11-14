package com.chuangxin.musicroom.client.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuangxin.musicroom.core.entity.Help;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
public interface HelpMapper extends BaseMapper<Help> {

    IPage<Help> getList(Page<Help> page, Map<String, Object> param);
    
}