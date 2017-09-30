package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.SysMenus;

public interface SysMenusMapper {
    int deleteByPrimaryKey(Integer menuId);

    int deleteByMenuId(@Param("menuId") Integer menuId, @Param("parentId") Integer parentId);

    int insert(SysMenus record);

    int insertSelective(SysMenus record);

    SysMenus selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(SysMenus record);

    int updateByPrimaryKey(SysMenus record);

    List<SysMenus> getAllMenus(Map<String, Object> paramsMap);
    
    List<Map<String,String>> getParentMenus();
    
    int getMaxSort(Integer menuId);
    
    int selectTotalRecord(Map<String, Object> paramsMap);
    
    int countByMenuName(String menuName);
}
