package com.ym.iadpush.manage.services.tissue.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.SysMenus;
import com.ym.iadpush.manage.mapper.SysMenusMapper;
import com.ym.iadpush.manage.services.tissue.IMenuMgr;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class MenuMgrSv implements IMenuMgr {

    private @Autowired SysMenusMapper menuMapper;

    public List<SysMenus> getAllMenus(Map<String, Object> paramsMap) {
        return menuMapper.getAllMenus(paramsMap);
    }

    public int updateMenu(SysMenus menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    public synchronized int insertMenu(SysMenus menu) {
    	int sort = 1;
    	if (menu.getParentId() != null && menu.getParentId() != 0) {
    		sort = menuMapper.getMaxSort(menu.getParentId()) + 1;
    	}
    	
    	menu.setSort(sort);
    	
        return menuMapper.insert(menu);
    }

    public int deleteMenu(int menuId) {
        return menuMapper.deleteByMenuId(menuId, menuId);
    }

	public int selectTotalRecord(Map<String, Object> paramsMap) {
		return menuMapper.selectTotalRecord(paramsMap);
	}

	public List<Map<String, String>> getParentMenus() {
		return menuMapper.getParentMenus();
	}

	public SysMenus selectByPrimaryKey(Integer menuId) {
		return menuMapper.selectByPrimaryKey(menuId);
	}

	public int countByMenuName(String menuName) {
		return menuMapper.countByMenuName(menuName);
	}
}
