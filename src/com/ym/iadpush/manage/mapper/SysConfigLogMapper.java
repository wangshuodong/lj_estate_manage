package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysConfigLog;

public interface SysConfigLogMapper {
    int insert(SysConfigLog confinLog);
    
    List<SysConfigLog> getAllConfigLog(Map params);
    
    int totalCount(Map params);
}
