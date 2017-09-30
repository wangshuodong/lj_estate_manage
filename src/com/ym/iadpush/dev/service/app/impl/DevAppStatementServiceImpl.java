package com.ym.iadpush.dev.service.app.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.dev.entity.DevAppStatement;
import com.ym.iadpush.dev.entity.DevAppStatementDetail;
import com.ym.iadpush.dev.mapper.DevAppMapper;
import com.ym.iadpush.dev.service.app.DevAppStatementService;
import com.ym.iadpush.manage.entity.App;

@Service
public class DevAppStatementServiceImpl implements DevAppStatementService {
    
    @Autowired
    private DevAppMapper devAppMapper;

    public QueryResult query(Map<String, Object> params) {
        
        QueryResult result = new QueryResult();

        List<DevAppStatement> list = devAppMapper.queryDevAppStatement(params);
        if(list != null && list.size() > 0){
            result.setData(list);
            int count = devAppMapper.countDevAppStatement(params);
            result.setCount(count);
            DevAppStatement statement = devAppMapper.sumDevAppStatement(params);
            result.setCollect(statement);
        }
        
        return result;
    }

    
    public QueryResult queyAppStatementDetal(Map<String, Object> params) {
        QueryResult result = new QueryResult();
        
        List<DevAppStatementDetail> list = devAppMapper.queryDevAppStatementDetail(params);
        
        if(list != null && list.size() > 0){
            result.setData(list);
            int count = devAppMapper.countDevAppStatementDetail(params);
            DevAppStatementDetail detail = devAppMapper.sumDevAppStatementDetail(params);
            result.setCollect(detail);
            result.setCount(count);
        }
        
        return result;
    }


	public List<App> queryAllApp() {
		return devAppMapper.queryAllApp();
	}
    
    

}
