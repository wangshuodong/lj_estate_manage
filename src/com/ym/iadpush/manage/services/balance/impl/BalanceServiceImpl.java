package com.ym.iadpush.manage.services.balance.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.base.Constants;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Balance;
import com.ym.iadpush.manage.entity.BalanceCollect;
import com.ym.iadpush.manage.entity.CurrentAccountCollect;
import com.ym.iadpush.manage.entity.CurrentAccountDetail;
import com.ym.iadpush.manage.entity.Earn;
import com.ym.iadpush.manage.entity.UserBalance;
import com.ym.iadpush.manage.mapper.AccountMapper;
import com.ym.iadpush.manage.mapper.AwardMapper;
import com.ym.iadpush.manage.mapper.BalanceMapper;
import com.ym.iadpush.manage.mapper.CurrentAcountCollectMapper;
import com.ym.iadpush.manage.mapper.CurrentAcountDetailMapper;
import com.ym.iadpush.manage.mapper.EarnMapper;
import com.ym.iadpush.manage.mapper.SysConfigMapper;
import com.ym.iadpush.manage.mapper.SysUsersMapper;
import com.ym.iadpush.manage.services.balance.IBalanceService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class BalanceServiceImpl implements IBalanceService {

    private @Autowired
    BalanceMapper balanceMapper;
    private @Autowired
    EarnMapper earnMapper;
    private @Autowired
    AwardMapper awardMapper;
    private @Autowired
    SysUsersMapper userMapper;
    private @Autowired
    SysConfigMapper configMapper;
    private @Autowired
    AccountMapper accountMapper;
    private @Autowired
    CurrentAcountCollectMapper currentAcountCollectMapper;

    private @Autowired
    CurrentAcountDetailMapper currentAcountDetailMapper;

    @Override
    public List<UserBalance> findUserBlances(Map<String, Object> paramsMap) {
        return balanceMapper.findUserBlances(paramsMap);
    }

    @Override
    public int selectTotalRecord(Map<String, Object> paramsMap) {
        return balanceMapper.selectTotalRecord(paramsMap);
    }

    @Override
    public BalanceCollect collectUserBlances(Map<String, Object> paramsMap) {
        return balanceMapper.collectUserBlances(paramsMap);
    }

    @Override
    public int settleAccount(String edate, int userId) {
        List<Earn> earns = earnMapper.findByEdate(edate, 0);

        if (null == earns || earns.isEmpty()) {
            return 0;
        }

        String settleDate = edate + " 23:59:59";
        List<Map<String, Object>> awardList = awardMapper.findUnSettleBonus(settleDate);
        Map<String, Object> awardMap = new HashMap<String, Object>();
        for (Map<String, Object> map : awardList) {
            awardMap.put(map.get("uid").toString(), map.get("bonus"));
        }

        List<Balance> balances = new ArrayList<Balance>();
        DecimalFormat df = new DecimalFormat("#0.00");
        String confiVal = configMapper.selectByKey("earnValue");
        double earnValue = 0;
        if (StringUtils.isNotBlank(confiVal)) {
            earnValue = Double.parseDouble(confiVal);
        } else {
            return 0;
        }

        for (Earn earn : earns) {
            int uid = earn.getUid();

            double bonus = 0;
            if (awardMap != null) {
                Object tmp = awardMap.get(String.valueOf(uid));
                if (null != tmp) {
                    bonus = Double.valueOf(tmp.toString());
                }
            }

            // 税前
            double taxage = earn.getEarnMoney() + bonus;
            if (taxage > earnValue) {
                double real = earn.getEarnMoney() * Constants.TAX_RATE + bonus;
                Balance balance = new Balance();
                balance.setUid(uid);
                balance.setEarn(Double.valueOf(df.format(earn.getEarnMoney())));
                balance.setBonus(Double.valueOf(df.format(bonus)));
                balance.setEarnNofee(Double.valueOf(df.format(real)));
                balance.setSdate(earn.getMindate());
                balance.setEdate(edate);
                balance.setOperuser(userId);
                balance.setAddtime(new Timestamp(System.currentTimeMillis()));

                // 查询开发者类型 若类型为 非开发者 则拒绝此帐单
                String devType = userMapper.findDevType(uid);
                balance.setStatus("dev".equals(devType) ? 0 : 2);

                balances.add(balance);

                // 更新earn中edate之前的数据
                earnMapper.updateStatusByEdate(uid, edate, 1);
                // 更新earn中settleDate之前的数据
                awardMapper.updateStatusByDate(uid, settleDate, 1);
            }
        }

        if (null == balances || balances.isEmpty()) {
            return 0;
        }

        return balanceMapper.batchInsert(balances);
    }

    public int updateStatus(Map<String, Object> paramsMap) {
        return balanceMapper.updateStatus(paramsMap);
    }

    public int updateStatusByBatch(Map<String, Object> paramsMap) {
        return balanceMapper.updateStatusByBatch(paramsMap);
    }

    @Override
    public int updateCurrentAccountCollect(CurrentAccountCollect currentaccountCollect) {
        int i = currentAcountCollectMapper.updateCurrentaccountCollect(currentaccountCollect);
        return i;
    }

    @Override
    public int updateCurrentAccountDetail(double receMoney, double payMoney, String month, Date addate, int companyId) {
        return 0;
    }

    @Override
    public CurrentAccountCollect getCurrentAccountCollect(Integer companyId, String month) {

        CurrentAccountCollect currentaccountCollect = currentAcountCollectMapper.getCurrentAccountCollect(companyId,
                month);

        if (currentaccountCollect == null) {
            currentaccountCollect = currentAcountCollectMapper.getLastCurrentAccountCollect(companyId);
        }

        return currentaccountCollect;
    }

    @Override
    public int saveCurrentAccountCollect(CurrentAccountCollect currentaccountCollect) {
        int i = currentAcountCollectMapper.saveCurrentAccountCollect(currentaccountCollect);
        return i;
    }

    @Override
    public int updateCurrentAccountDetail(CurrentAccountDetail detail) {
        int i = currentAcountDetailMapper.updateCurrentAccountDetail(detail);
        return i;
    }

    @Override
    public CurrentAccountDetail getCurrentAccountDetail(Integer companyId, Date addate) {
        CurrentAccountDetail detail = currentAcountDetailMapper.getCurrentAccountDetail(companyId, addate);
        return detail;
    }

    @Override
    public int saveCurrentAccountDetail(CurrentAccountDetail detail) {
        int i = currentAcountDetailMapper.saveCurrentAccountDetail(detail);
        return i;
    }

    @Override
    public List<CurrentAccountCollect> queryCurrentAccountCollect(Map<String, Object> paramMap) {
        List<CurrentAccountCollect> list = currentAcountCollectMapper.queryCurrentAccountCollect(paramMap);
        return list;
    }

    @Override
    public Integer queryCurrentAccountCollectCount(Map<String, Object> paramMap) {
        Integer in = currentAcountCollectMapper.queryCurrentAccountCollectCount(paramMap);
        return in;
    }

    @Override
    public List<CurrentAccountDetail> queryCurrentAccountDetail(Map<String, Object> paramMap) {
        List<CurrentAccountDetail> list = currentAcountDetailMapper.queryCurrentAccountDetail(paramMap);
        return list;
    }

    @Override
    public Integer queryCurrentAccountDetailCount(Map<String, Object> paramMap) {
        Integer in = currentAcountDetailMapper.queryCurrentAccountDetailCount(paramMap);
        return in;
    }

    @Override
    public CurrentAccountDetail getLatelyCurrentAccountDetail(Integer companyId, Date addate) {
        CurrentAccountDetail currentAccountDetail = currentAcountDetailMapper.getLatelyCurrentAccountDetail(companyId,
                addate);
        return currentAccountDetail;
    }

    @Override
    public QueryResult getAccountCollectDataStatement(Map<String, Object> paramMap) {
        QueryResult result = new QueryResult();

        result.setCollect(currentAcountCollectMapper.getAccountCollectDataStatement(paramMap));

        return result;
    }

    @Override
    public QueryResult getAccountDetailDataStatement(Map<String, Object> paramMap) {
        QueryResult result = new QueryResult();

        result.setCollect(currentAcountDetailMapper.getDetailCollectDataStatement(paramMap));

        return result;
    }

    @Override
    public Map<String,Object> getCountAccountData(Map<String,Object> params) {
        return accountMapper.getCountAccountData(params);
    }
}
