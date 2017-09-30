package com.ym.iadpush.manage.services.apply.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.mapper.AppMapper;
import com.ym.iadpush.manage.services.apply.IApplyMgr;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class ApplyMgrImpl implements IApplyMgr {

	@Autowired
    private AppMapper appMapper;
	
	@Override
	public QueryResult queryDataStatement(Map<String, Object> params) {
		QueryResult result = new QueryResult();
        result.setData(appMapper.getAllApp(params));
        result.setCount(appMapper.getCountApp(params));
        return result;
	}


	@Override
	public int updateAppStatus(Map<String, Object> params) {
		return appMapper.updateAppStatus(params);
	}

	@Override
	public QueryResult queryAllApp(Map<String, Object> params) {
		// TODO Auto-generated method stub
		QueryResult result = new QueryResult();
		List<App> list = appMapper.queryFormApp(params);
		if(list != null && list.size() > 0){
			int count = appMapper.countFormApp(params);
			result.setCount(count);
			result.setData(list);
		}
		return result;
	}

	@Override
	public App getTotalEarnAndIcount(Map<String, Object> params) {
		return appMapper.getTotalEarnAndIcount(params);
	}


	@Override
	public QueryResult queryDataStatementForDev(Map<String, Object> params) {
		QueryResult result = new QueryResult();
        result.setData(appMapper.getAllAppForDev(params));
        result.setCount(appMapper.getCountAppForDev(params));
        return result;
	}


	@Override
	public App getTotalEarnAndIcountForDev(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appMapper.getTotalEarnAndIcountForApp(params);
	}


	@Override
	public Integer updateGreenYellow(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appMapper.updateGreenYellow(params);
	}

}
