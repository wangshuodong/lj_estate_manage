package com.ym.iadpush.dev.service.app;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.entity.SysUsers;

public interface DevAppStatementService {
    
    /**
     * 查询开发者应用统计
     * @Author TULIANGCHENG 2014-4-24 下午1:42:07
     * @param params
     * @return
     */
    QueryResult     query(Map<String,Object> params);
    
    /**
     * 查询开发者应用明细
     * @Author TULIANGCHENG 2014-4-24 下午1:42:23
     * @param params
     * @return
     */
    QueryResult     queyAppStatementDetal(Map<String,Object> params);
    
    /**
	 * 查询所有App
	 * @Author TULIANGCHENG 2014-5-12 下午6:08:23
	 * @return
	 */
	List<App> queryAllApp();
	
}
