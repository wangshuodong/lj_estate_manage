/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.OutBill;
import com.ym.iadpush.manage.entity.OutBillStatement;

public interface OutBillMapper {
    Integer insertOutBill(OutBill outBill);

    OutBill getOutBillById(Integer id);

    List<OutBill> getAllOutBills(Map<String, Object> paramMap);

    OutBill getMaxOutBillCode(String date);

    Integer getAllOutBillsCount(Map<String, Object> paramMap);

    OutBillStatement getAllOutBillsCollect(Map<String, Object> paramMap);
}
