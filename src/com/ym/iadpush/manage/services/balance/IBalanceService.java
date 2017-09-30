package com.ym.iadpush.manage.services.balance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.BalanceCollect;
import com.ym.iadpush.manage.entity.CurrentAccountCollect;
import com.ym.iadpush.manage.entity.CurrentAccountDetail;
import com.ym.iadpush.manage.entity.UserBalance;

public interface IBalanceService {
    List<UserBalance> findUserBlances(Map<String, Object> paramsMap);

    int selectTotalRecord(Map<String, Object> paramsMap);

    BalanceCollect collectUserBlances(Map<String, Object> paramsMap);

    int settleAccount(String edate, int userId);

    int updateStatus(Map<String, Object> paramsMap);

    int updateStatusByBatch(Map<String, Object> paramsMap);

    int updateCurrentAccountCollect(CurrentAccountCollect currentaccountCollect);

    int saveCurrentAccountCollect(CurrentAccountCollect currentaccountCollect);

    int updateCurrentAccountDetail(double receMoney, double payMoney, String month, Date addate, int companyId);

    CurrentAccountCollect getCurrentAccountCollect(@Param("companyId") Integer companyId, @Param("month") String month);

    int updateCurrentAccountDetail(CurrentAccountDetail detail);

    CurrentAccountDetail getCurrentAccountDetail(Integer companyId, Date addate);

    int saveCurrentAccountDetail(CurrentAccountDetail detail);

    List<CurrentAccountCollect> queryCurrentAccountCollect(Map<String, Object> paramMap);

    Integer queryCurrentAccountCollectCount(Map<String, Object> paramMap);

    List<CurrentAccountDetail> queryCurrentAccountDetail(Map<String, Object> paramMap);

    Integer queryCurrentAccountDetailCount(Map<String, Object> paramMap);

    /**
     * 最近一次明细账，不包括今天
     * 
     * @Author 2014-8-13 下午4:13:12
     * @param companyId
     * @param addate
     * @return
     */
    CurrentAccountDetail getLatelyCurrentAccountDetail(Integer companyId, Date addate);

    QueryResult getAccountCollectDataStatement(Map<String, Object> paramMap);

 
    QueryResult getAccountDetailDataStatement(Map<String, Object> paramMap);
    /**
     * 首页数据统计
     * @Author lixingbiao 2017年7月12日 下午3:46:41
     * @param month
     * @return
     */
    Map<String, Object> getCountAccountData(Map<String,Object> params);

}
