/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.BillAccountDataStatement;

/**
 * @Author: lixingbiao 2017-6-11 下午4:37:32
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public interface BillAccountMapper {
    //删除账单根据参数
    Integer deleteBillAccountByParams(Map<String, Object> paramMap);
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    List<String> getBillType();
    Integer insertBillAccount(BillAccount billAccount);

    List<BillAccount> getBillAccounts(Map<String, Object> paramMap);

    BillAccount getPrecedingMonthBillMoney(int proprietorId, String month);

    BillAccount getBillAccountById(Integer id);

    BillAccount getBillAccountByBill_entry_id(String bill_entry_id);

    BillAccount getMaxBill_entry_id(@Param("date") String date);

    Integer updateBillAccount(@Param("billAccount") BillAccount billAccount,
            @Param("bill_entry_id") String bill_entry_id);

    List<BillAccount> getBillAccountsBySendStatus(Map<String, Object> paramMap);

    int getBillAccountsCount(Map<String, Object> paramMap);

    BillAccountDataStatement getBillAccountCollect(Map<String, Object> paramMap);
    
    List<BillAccount> getPrintOne(Map<String, Object> paramMap);
    
    List<BillAccount> getPrintMore(Map<String, Object> paramMap);

}
