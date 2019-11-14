package com.chuangxin.musicroom.client.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuangxin.musicroom.core.entity.User;

/**
 * @author DaiDong
 * @since 2019-09-23
 * @email 755144556@qq.com
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getList(Page<User> page, Map<String, Object> param);

    int insertReturnId(User user);

}