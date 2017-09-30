package com.ym.iadpush.manage.services.sysconfig;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysConfigLog;


public interface ISysConfigLog {
    int insert(SysConfigLog confinLog);
    
    List<SysConfigLog> getAllConfigLog(Map params);
    
    int totalCount(Map params);
}
