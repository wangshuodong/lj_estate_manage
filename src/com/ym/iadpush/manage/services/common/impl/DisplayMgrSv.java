package com.ym.iadpush.manage.services.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.Cache;
import org.mybatis.caches.ehcache.EhcacheCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.SysPosts;
import com.ym.iadpush.manage.entity.SysRoles;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.mapper.SysPostsMapper;
import com.ym.iadpush.manage.mapper.SysRolesMapper;
import com.ym.iadpush.manage.mapper.SysUsersMapper;
import com.ym.iadpush.manage.services.common.IDisplayMgr;

@Service
public class DisplayMgrSv implements IDisplayMgr {

    private String DISPLAY_CACHE_KEY = "DISPLAY_CACHE_KEY";
    private @Autowired SysUsersMapper usersMapper;
    private @Autowired SysRolesMapper rolesMapper;
    private @Autowired SysPostsMapper postsMapper;


    private Cache getCache(Class<?> mapper) {
        return new EhcacheCache(mapper.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Integer, String> displayUser() {
        Map<Integer, String> result = null;
        // 获取缓存对象
        Cache cache = getCache(SysUsersMapper.class);
        String key = DISPLAY_CACHE_KEY + "_DISPLAY_USER";
        Object cacheResult = cache.getObject(key);
        if (cacheResult != null) {
            result = (Map<Integer, String>) cacheResult;
        } else {
            result = new HashMap<Integer, String>();
            List<SysUsers> users = usersMapper.getAllUsers();
            for (SysUsers user : users) {
                result.put(user.getUserId(), user.getNick());
            }
            cache.putObject(key, result);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Integer, String> displayRole() {
        Map<Integer, String> result = null;
        // 获取缓存对象
        Cache cache = getCache(SysUsersMapper.class);
        String key = DISPLAY_CACHE_KEY + "_DISPLAY_ROLE";
        Object cacheResult = cache.getObject(key);
        if (cacheResult != null) {
            result = (Map<Integer, String>) cacheResult;
        } else {
            result = new HashMap<Integer, String>();
            List<SysRoles> roles = rolesMapper.getAllRoles();
            for (SysRoles role : roles) {
                result.put(role.getRoleId(), role.getRoleName());
            }
            cache.putObject(key, result);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Integer, String> displayPost() {
        Map<Integer, String> result = null;
        // 获取缓存对象
        Cache cache = getCache(SysUsersMapper.class);
        String key = DISPLAY_CACHE_KEY + "_DISPLAY_POST";
        Object cacheResult = cache.getObject(key);
        if (cacheResult != null) {
            result = (Map<Integer, String>) cacheResult;
        } else {
            result = new HashMap<Integer, String>();
            List<SysPosts> posts = postsMapper.getAllPosts();
            for (SysPosts post : posts) {
                result.put(post.getPostId(), post.getPostName());
            }
            cache.putObject(key, result);
        }
        return result;
    }
}
