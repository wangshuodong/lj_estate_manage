package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysRoleMenuKey;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(SysRoleMenuKey key);

    int insert(SysRoleMenuKey record);

    int insertSelective(SysRoleMenuKey record);
    
    int batchInsert(List<SysRoleMenuKey> roleMenu);

    List<SysRoleMenuKey> selectByRoleId(Integer roleId);
    
    int deleteById(Integer roleId);
    
    List<Map<String,String>> selectMenuByRoleId(Integer roleId);
    
    List<Map<String,String>> selectAllMenus();
    
    List<Map<String,String>> selectMenusByType(String type);
}
