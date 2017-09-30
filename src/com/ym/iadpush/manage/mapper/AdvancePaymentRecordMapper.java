/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.AdvancePaymentRecord;
import com.ym.iadpush.manage.entity.AdvancePaymentRecordStatement;

public interface AdvancePaymentRecordMapper {
    Integer insertAdvancePaymentRecord(AdvancePaymentRecord advancePaymentRecord);

    AdvancePaymentRecord getMaxAdvancePaymentRecordCode(String date);

    List<AdvancePaymentRecord> getAllAdvancePaymentRecords(Map<String, Object> paramMap);

    Integer getAllAdvancePaymentRecordsCount(Map<String, Object> paramMap);

    AdvancePaymentRecordStatement getAllAdvancePaymentRecordsCollect(Map<String, Object> paramMap);
}
