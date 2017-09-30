package com.ym.iadpush.manage.services.common;

import java.util.List;

import com.ym.iadpush.manage.entity.SysConfig;

public interface IConfigMgr {
	String selectByKey(String key);

    /**
     * @Author LiXingBiao 2014-4-15 下午3:53:43
     * @param key
     * @return
     */
    List<SysConfig> getById(String key);

    /**
     * @Author LiXingBiao 2014-4-15 下午3:54:11
     * @param cfgId
     * @return
     */
    Object getByParentCfg(String cfgId);
}
