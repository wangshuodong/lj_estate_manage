package com.ym.iadpush.manage.services.earn.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.utils.DateUtils;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.WeekData;
import com.ym.iadpush.manage.mapper.IadpushEarnMapper;
import com.ym.iadpush.manage.mapper.WeekDataMapper;
import com.ym.iadpush.manage.services.earn.IEarnService;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class IEarnServiceImpl implements IEarnService {

    @Autowired
    private IadpushEarnMapper earnMapper;
    @Autowired
    private WeekDataMapper dataMapper;

    public Double sumEarnByDate(String startDateStr, String endDateStr) {
        Map<String, Object> params = new HashMap<String, Object>();
        Date startDate = null;
        Date endDate = null;
        Subject subject = SecurityUtils.getSubject();
        SysUsers user = null;
        if (subject.isAuthenticated()) {
            // PrincipalCollection collection = subject.getPrincipals();
            // return (SysUsers) collection.asList().get(2);
        	user = (SysUsers) subject.getPrincipal();
        }
        
        if(user.getRoles().get(0).getType().equalsIgnoreCase("outService")){
        	params.put("serviceId", user.getUserId());
        }
        try {
            if(startDateStr != null){
                startDate = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
            }
            if(endDateStr != null){
                endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd");
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return earnMapper.sumEarnByDate(params);
    }

    @Override
    public WeekData queryWeekData(String startDateStr, String endDateStr) {

        Map<String, Object> params = new HashMap<String, Object>();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
            endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd");
        } catch (Exception e) {
        }
        Subject subject = SecurityUtils.getSubject();
        SysUsers user = null;
        if (subject.isAuthenticated()) {
            // PrincipalCollection collection = subject.getPrincipals();
            // return (SysUsers) collection.asList().get(2);
        	user = (SysUsers) subject.getPrincipal();
        }
        
        if(user.getRoles().get(0).getType().equalsIgnoreCase("outService")){
        	params.put("serviceId", user.getUserId());
        }
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        WeekData wd = dataMapper.queryWeekDataByDeverReport(params);
        return wd == null ? new WeekData() : wd;
    }

}
