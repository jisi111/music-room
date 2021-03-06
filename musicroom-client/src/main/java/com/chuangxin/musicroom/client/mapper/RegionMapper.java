package com.chuangxin.musicroom.client.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuangxin.musicroom.core.dto.RegionTreeNode;
import com.chuangxin.musicroom.core.entity.Region;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
public interface RegionMapper extends BaseMapper<Region> {

    IPage<Region> getList(Page<Region> page, Map<String, Object> param);

    List<RegionTreeNode> getListByLevel(Integer level);

    List<RegionTreeNode> getListByPcode(String pcode);
    
}