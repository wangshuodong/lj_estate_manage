package com.ym.iadpush.manage.mapper;

import java.util.List;

import com.ym.iadpush.manage.entity.SysUserRoleKey;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(SysUserRoleKey key);

    int insert(SysUserRoleKey record);

    int insertSelective(SysUserRoleKey record);

    List<SysUserRoleKey> selectByUserId(int userId);

    int deleteByUserId(Integer userId);
}
