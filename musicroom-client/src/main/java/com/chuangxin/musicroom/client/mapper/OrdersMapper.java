package com.chuangxin.musicroom.client.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuangxin.musicroom.core.entity.Orders;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    IPage<Orders> getList(Page<Orders> page, Map<String, Object> param);
    
}