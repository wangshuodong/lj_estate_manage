package com.ym.iadpush.manage.mapper;

import com.ym.iadpush.manage.entity.SysUserPostKey;

public interface SysUserPostMapper {
    int deleteByPrimaryKey(SysUserPostKey key);

    int insert(SysUserPostKey record);

    int insertSelective(SysUserPostKey record);

    int deleteByUserId(Integer userId);
}
