package com.ym.iadpush.manage.mapper;

import java.util.Map;

import com.ym.iadpush.manage.entity.WeekData;

public interface WeekDataMapper {
    
    
    WeekData queryWeekDataByDeverReport(Map<String,Object> params);

}
