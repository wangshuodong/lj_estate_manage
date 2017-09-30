package com.ym.iadpush.manage.services.datamanager.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.mapper.AppMapper;
import com.ym.iadpush.manage.mapper.DataManagerMapper;
import com.ym.iadpush.manage.mapper.DevStatementMapper;
import com.ym.iadpush.manage.mapper.WeekDataMapper;
import com.ym.iadpush.manage.services.datamanager.DataManagerService;

@Service
public class DataManagerServiceImpl implements DataManagerService {

    @Autowired
    private WeekDataMapper dataMapper;
    @Autowired
    private DataManagerMapper dataManagerMapper;
    @Autowired
    private DevStatementMapper devStatementMapper;
    @Autowired
    private AppMapper appMapper;

    public DevStatementMapper getDevStatementMapper() {
        return devStatementMapper;
    }

    public void setDevStatementMapper(DevStatementMapper devStatementMapper) {
        this.devStatementMapper = devStatementMapper;
    }

    public WeekDataMapper getDataMapper() {
        return dataMapper;
    }

    public void setDataMapper(WeekDataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    public DataManagerMapper getDataManagerMapper() {
        return dataManagerMapper;
    }

    public void setDataManagerMapper(DataManagerMapper dataManagerMapper) {
        this.dataManagerMapper = dataManagerMapper;
    }

    // *************************************************************setter,getter分割线***********************************************************//

    public QueryResult queryDataStatement(Map<String, Object> params) {
        QueryResult result = new QueryResult();
        result.setData(dataManagerMapper.queryDataStatement(params));
        result.setCount(dataManagerMapper.countDataStatement(params));
        result.setCollect(dataManagerMapper.sumDataStatement(params));
        return result;
    }

    public QueryResult queryDevStatement(Map<String, Object> params) {
        QueryResult result = new QueryResult();
        result.setData(devStatementMapper.queryDevStatement(params));
        result.setCount(devStatementMapper.countDevStatement(params));
        result.setCollect(devStatementMapper.sumDevStatement(params));
        return result;
    }

    public QueryResult queryDevStatementDetail(Map<String, Object> params) {
        QueryResult result = new QueryResult();
        result.setData(devStatementMapper.queryDevStatementDetail(params));
        result.setCount(devStatementMapper.countDevStatementDetail(params));
        result.setCollect(devStatementMapper.sumDevStatementDetail(params));
        return result;
    }

	public QueryResult queryAppStatement(Map<String, Object> params) {
		// TODO Auto-generated method stub
		QueryResult result = new QueryResult();
		result.setData(devStatementMapper.queryAppStatement(params));
		result.setCount(devStatementMapper.countQueryAppStatement(params));
		result.setCollect(devStatementMapper.sumAppStatement(params));
		return result;
	}

	public int updateAppStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appMapper.updateAppStatus(params);
	}

	public QueryResult dataListener(Map<String, Object> params) {
		// TODO Auto-generated method stub
		QueryResult result = new QueryResult();
		result.setData(dataManagerMapper.dataReportSelect(params));
		result.setCount(dataManagerMapper.dataReportSelectCount(params));
		return result;
	}

}
