package com.ym.iadpush.dev.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.dev.entity.DevAppStatement;
import com.ym.iadpush.dev.entity.DevAppStatementDetail;
import com.ym.iadpush.manage.entity.App;

public interface DevAppMapper {
    
    List<DevAppStatement> queryDevAppStatement(Map<String,Object> params);
    
    Integer countDevAppStatement(Map<String,Object> params);
    
    DevAppStatement sumDevAppStatement(Map<String,Object> params);
    
    List<DevAppStatementDetail> queryDevAppStatementDetail(Map<String,Object> params);
    
    DevAppStatementDetail sumDevAppStatementDetail(Map<String,Object> params);
    
    Integer countDevAppStatementDetail(Map<String,Object> params);
    
    List<App> queryAllApp();
    
    List<App> queryByUsers(Map<String,Object> params);
    
    List<App> queryByApps(Map<String,Object> params);
    
    int updateEarn(Map<String,Object> params);
    
    int updateCpacount(Map<String,Object> params);
    
    int updateDevReport(Map<String,Object> params);
    
    List<App> queryNotInAppOfEarn(Map<String,Object> params);
    
    App queryById(int id);
    
    int updateDevReportByParams(Map<String,Object> params);
    
    int updateCpacountByParams(Map<String,Object> params);
    
    List<App> queryUpdatedApp(Map<String,Object> params);
    
    App queryFormEarnByApp(Integer appid);
    
    App queryFormEarnByParams(Map<String,Object> params);
    
    App queryFormCpacount(Map<String,Object> params);
    
    App queryFormDeveloper(Map<String,Object> params);
    
}
