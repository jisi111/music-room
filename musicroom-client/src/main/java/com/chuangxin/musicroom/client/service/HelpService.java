package com.chuangxin.musicroom.client.service;

import java.util.Map;
import com.chuangxin.musicroom.core.entity.Help;
import com.chuangxin.musicroom.core.dto.BaseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
public interface HelpService extends IService<Help> {
    
    BaseResult getList(Page<Help> page, Map<String, Object> map);

}
