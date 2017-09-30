/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.ExchangEapplyRecord;
import com.ym.iadpush.manage.entity.ExchangEapplyRecordStatementStatement;

public interface ExchangEapplyRecordMapper {
    Integer insertExchangEapplyRecord(ExchangEapplyRecord exchangEapplyRecord);

    ExchangEapplyRecord getMaxExchangEapplyRecordCode(String date);

    ExchangEapplyRecordStatementStatement getAllExchangEapplyRecordCollect(Map<String, Object> paramMap);

    List<ExchangEapplyRecord> getAllExchangEapplyRecord(Map<String, Object> paramMap);

    Integer getAllExchangEapplyRecordCount(Map<String, Object> paramMap);

    ExchangEapplyRecord getExchangEapplyRecordById(Integer id);

    Integer updateExchangEapplyRecord(@Param("exchangEapplyRecord") ExchangEapplyRecord exchangEapplyRecord,
            @Param("id") Integer id);
}
