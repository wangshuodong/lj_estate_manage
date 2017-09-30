package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.AppStatement;

public interface AppStatementMapper {
    
 
    List<Object> queryAppStatement(Map<String, Object> params);

  
    int countAppStatement(Map<String, Object> params);
 
    AppStatement sumAppStatement(Map<String, Object> params);

}
