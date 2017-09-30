package com.ym.iadpush.manage.mapper;

import java.util.List;

import com.ym.iadpush.manage.entity.SysPosts;

public interface SysPostsMapper {
    int deleteByPrimaryKey(Integer postId);

    int insert(SysPosts record);

    int insertSelective(SysPosts record);

    SysPosts selectByPrimaryKey(Integer postId);

    List<SysPosts> getAllPosts();

    int updateByPrimaryKeySelective(SysPosts record);

    int updateByPrimaryKey(SysPosts record);
}
