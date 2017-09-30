package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.Trend24hour;

public interface Trend24hourMapper {
    
    List<Trend24hour> queryTrend24hour(Map<String, Object> params);
    
    List<Map<String,String>> queryPayTrend24hour(Map<String, Object> params);
}
