package com.ym.iadpush.manage.services.trend24hour.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.Trend24hour;
import com.ym.iadpush.manage.mapper.Trend24hourMapper;
import com.ym.iadpush.manage.services.trend24hour.Trend24hourService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class Trend24hourServiceImpl implements Trend24hourService {
    
    @Autowired
    private Trend24hourMapper trend24hourMapper;

    public List<Trend24hour> queryTrend24hour(Map<String, Object> params) {
        return trend24hourMapper.queryTrend24hour(params);
    }

    public Trend24hourMapper getTrend24hourMapper() {
        return trend24hourMapper;
    }

    public void setTrend24hourMapper(Trend24hourMapper trend24hourMapper) {
        this.trend24hourMapper = trend24hourMapper;
    }

	public List<Map<String,String>> queryPayTrend24hour(Map<String, Object> params) {
		return trend24hourMapper.queryPayTrend24hour(params);
	}

}
