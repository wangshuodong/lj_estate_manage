package com.ym.iadpush.dev.service.earn;

import com.ym.iadpush.dev.entity.DevEarn;
import com.ym.iadpush.dev.entity.DevQuickReport;


public interface DevEarnService {
    
    /**
     * 根据时间段查询某开发者的收入
     * @Author TULIANGCHENG 2014-4-24 下午5:02:19
     * @param startDate
     * @param endDate
     * @param uid
     * @return
     */
    public DevEarn selectEarnByDate(String startDate,String endDate,int uid);
    
    /**
     * 查看开发者快捷报表
     * @Author TULIANGCHENG 2014-4-24 下午5:55:24
     * @param uid
     * @return
     */
    public DevQuickReport selectQuickReport(int uid);
    

}
