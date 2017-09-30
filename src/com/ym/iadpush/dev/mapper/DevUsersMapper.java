package com.ym.iadpush.dev.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysUsers;


public interface DevUsersMapper {
   
 
    int updateUserInfo(Map<String,Object> params);
    
 
    SysUsers queryUsers(Map<String,Object> params);
    
 
    Integer register(SysUsers users);
    

    void addToRole(Map<String,Object> params);
    
 
    SysUsers selectByUserName(String username);
    
    /**
     * 更新用户信息
     * @param users
     * @return
     */
    int updateByPrimaryKeySelective(SysUsers users);
 
    public List<SysUsers> queryAllUsers();
    

    int updatePtPwd(Map<String,Object> params);
    
    
}
