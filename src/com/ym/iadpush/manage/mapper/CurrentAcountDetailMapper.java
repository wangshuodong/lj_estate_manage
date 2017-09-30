/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.AccountDetailDataStatement;
import com.ym.iadpush.manage.entity.CurrentAccountDetail;

public interface CurrentAcountDetailMapper {
    int updateCurrentAccountDetail(@Param("detail") CurrentAccountDetail detail);

    CurrentAccountDetail getCurrentAccountDetail(@Param("companyId") Integer companyId, @Param("addate") Date addate);

    int saveCurrentAccountDetail(CurrentAccountDetail detail);

    List<CurrentAccountDetail> queryCurrentAccountDetail(Map<String, Object> paramMap);

    Integer queryCurrentAccountDetailCount(Map<String, Object> paramMap);

    CurrentAccountDetail getLatelyCurrentAccountDetail(@Param("companyId") Integer companyId,
            @Param("addate") Date addate);

    AccountDetailDataStatement getDetailCollectDataStatement(Map<String, Object> paramMap);
}
