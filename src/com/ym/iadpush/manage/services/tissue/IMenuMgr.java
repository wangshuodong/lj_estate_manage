package com.ym.iadpush.manage.services.tissue;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysMenus;

public interface IMenuMgr {

    public List<SysMenus> getAllMenus(Map<String, Object> paramsMap);
    
    public int updateMenu(SysMenus menu);

    public int insertMenu(SysMenus menu);

    public int deleteMenu(int menuId);
    
    public List<Map<String,String>> getParentMenus();
    
    public SysMenus selectByPrimaryKey(Integer menuId);
    
    public int selectTotalRecord(Map<String, Object> paramsMap);
    
    public int countByMenuName(String menuName);
}
