package com.syp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syp.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-27 10:08:53
 */
public interface UserMapper extends BaseMapper<User> {

}

