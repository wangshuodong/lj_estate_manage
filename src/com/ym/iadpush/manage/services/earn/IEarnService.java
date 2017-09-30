package com.ym.iadpush.manage.services.earn;

import com.ym.iadpush.manage.entity.WeekData;



public interface IEarnService {
    

    public Double sumEarnByDate(String startDate,String endDate);
    

    public WeekData queryWeekData(String startDate,String endDate);

}
