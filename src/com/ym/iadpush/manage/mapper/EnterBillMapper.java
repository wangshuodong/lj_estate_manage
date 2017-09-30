/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.EnterBill;
import com.ym.iadpush.manage.entity.EnterBillStatement;

public interface EnterBillMapper {
    Integer insertEnterBill(EnterBill enterBill);

    Integer auditingEnterBill(@Param("enterBill") EnterBill enterBill, @Param("id") Integer id);

    EnterBill getEnterBillById(Integer id);

    List<EnterBill> getAllEnterBills(Map<String, Object> paramMap);

    Integer getAllEnterBillsCount(Map<String, Object> paramMap);

    EnterBill getMaxEnterBillCode(String date);

    EnterBillStatement getAllEnterBillsCollect(Map<String, Object> paramMap);

    List<EnterBill> getAllNoauditingEnterBills(Map<String, Object> paramMap);

    Integer updateEnterBill(@Param("enterBill") EnterBill enterBill, @Param("id") Integer id);

}
