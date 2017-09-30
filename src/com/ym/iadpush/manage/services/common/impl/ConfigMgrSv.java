package com.ym.iadpush.manage.services.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.SysConfig;
import com.ym.iadpush.manage.mapper.SysConfigMapper;
import com.ym.iadpush.manage.services.common.IConfigMgr;

@Service
public class ConfigMgrSv implements IConfigMgr {

    private @Autowired SysConfigMapper configMapper;

	@Override
	public String selectByKey(String key) {
		return configMapper.selectByKey(key);
	}

    @Override
    public List<SysConfig> getById(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.common.IConfigMgr#getByParentCfg(java.lang.String)
     */
    @Override
    public Object getByParentCfg(String cfgId) {
        // TODO Auto-generated method stub
        return null;
    }

}
