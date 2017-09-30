package com.ym.iadpush.dev.service.earn.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.DateUtils;
import com.ym.iadpush.dev.entity.DevEarn;
import com.ym.iadpush.dev.entity.DevQuickReport;
import com.ym.iadpush.dev.mapper.DevEarnMapper;
import com.ym.iadpush.dev.service.earn.DevEarnService;

@Service
public class DevEarnServiceImpl implements DevEarnService {
    
    @Autowired
    private DevEarnMapper devEarnMapper;
    
    /**
     * 查询已结算
     * @Author TULIANGCHENG 2014-4-24 下午5:19:31
     * @param params
     * @return
     */
    private Double selectSettledEarn( Map<String,Object> params){
        params.put("status", 1);
        return devEarnMapper.selectDevEarn(params);
    }
    
    /**
     * 查询未结算
     * @Author TULIANGCHENG 2014-4-24 下午5:19:56
     * @param params
     * @return
     */
    private Double selectUnsettledEarn( Map<String,Object> params){
        params.put("status", 0);
        return devEarnMapper.selectDevEarn(params);
    }
    
    /**
     * 查询已结算奖金
     * @Author TULIANGCHENG 2014-4-24 下午5:21:15
     * @param params
     * @return
     */
    private Double selectSettledAward(Map<String,Object> params){
        params.put("status", 1);
        return devEarnMapper.selectDevAward(params);
    }
    
    /**
     * 查询未结算奖金
     * @Author TULIANGCHENG 2014-4-24 下午5:21:43
     * @param params
     * @return
     */
    private Double selectUnsettledAward(Map<String,Object> params){
        params.put("status", 0);
        return devEarnMapper.selectDevAward(params);
    }

    public DevEarn selectEarnByDate(String startDate, String endDate, int uid) {
        
        DevEarn devEarn = new DevEarn();
        
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("uid", uid);
        
        devEarn.setSettledAward(this.selectSettledAward(params));
        devEarn.setUnsettledAward(this.selectUnsettledAward(params));
        devEarn.setSettledEarn(this.selectSettledEarn(params));
        devEarn.setUnsettledEarn(this.selectUnsettledEarn(params));
        
        return devEarn;
    }

    public DevEarnMapper getDevEarnMapper() {
        return devEarnMapper;
    }

    public void setDevEarnMapper(DevEarnMapper devEarnMapper) {
        this.devEarnMapper = devEarnMapper;
    }
    
    private Double getEarnByDate(String startDate,String endDate,int uid){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("uid", uid);
        return devEarnMapper.selectDevEarn(params);
    }

    @Override
    public DevQuickReport selectQuickReport(int uid) {
        
        DevQuickReport devQuickReport = new DevQuickReport();
        // 今日支出
        Double today = this.getEarnByDate(DateUtils.getDate(), DateUtils.getDate(), uid);

        // 昨日支出
        Double yestoday = this.getEarnByDate(DateUtils.getNDayBeforDate(1), DateUtils.getNDayBeforDate(1), uid);

        // 前七日支出
        Double seven = this.getEarnByDate(DateUtils.getNDayBeforDate(7),DateUtils.getBeforeDate(1), uid);

        String firstDayOfThisMonth = DateUtils.getFirstDayOfMonth(Integer.valueOf(DateUtils.getYear()),
                Integer.valueOf(DateUtils.getMonth()) - 1);

        // 本月支出
        Double month = this.getEarnByDate(firstDayOfThisMonth,DateUtils.getDate(), uid); 

        int lmonth = Integer.valueOf(DateUtils.getMonth());
        int lyear = Integer.valueOf(DateUtils.getYear());
        if (lmonth == 0) {
            lyear = lyear - 1;
            lmonth = 11;
        }

        String firstDayOfLastMonth = DateUtils.getFirstDayOfMonth(lyear, lmonth - 2);

        String endDayOfLastMonth = DateUtils.getLastDayOfMonth(lyear, lmonth - 2);

        // 上月支出
        Double lastmonth = this.getEarnByDate(firstDayOfLastMonth,endDayOfLastMonth, uid); 

        // 总支出
        Double total = this.getEarnByDate(null,null, uid); 
        
        devQuickReport.setToday(today);
        devQuickReport.setYestoday(yestoday);
        devQuickReport.setSevenday(seven);
        devQuickReport.setThismonth(month);
        devQuickReport.setLastmonth(lastmonth);
        devQuickReport.setAll(total);
        return devQuickReport;
    }

}
