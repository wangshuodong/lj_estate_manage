package com.ym.iadpush.manage.services.tissue.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.SysPosts;
import com.ym.iadpush.manage.mapper.SysPostsMapper;
import com.ym.iadpush.manage.services.tissue.IPostMgr;

@Service
public class PostMgrSv implements IPostMgr {

    private @Autowired SysPostsMapper postDao;

    public List<SysPosts> getAllPost() {
        return postDao.getAllPosts();
    }

}
