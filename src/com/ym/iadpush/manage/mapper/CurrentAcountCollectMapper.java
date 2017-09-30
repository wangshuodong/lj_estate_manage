/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.AccountCollectDataStatement;
import com.ym.iadpush.manage.entity.CurrentAccountCollect;

public interface CurrentAcountCollectMapper {
    int updateCurrentaccountCollect(@Param("currentCollect") CurrentAccountCollect collect);

    CurrentAccountCollect getCurrentAccountCollect(@Param("companyId") Integer companyId, @Param("month") String month);

    int saveCurrentAccountCollect(CurrentAccountCollect currentCollect);

    List<CurrentAccountCollect> queryCurrentAccountCollect(Map<String, Object> paramMap);

    Integer queryCurrentAccountCollectCount(Map<String, Object> paramMap);

    CurrentAccountCollect getLastCurrentAccountCollect(@Param("companyId") Integer companyId);

    AccountCollectDataStatement getAccountCollectDataStatement(Map<String, Object> paramMap);
    
    Object getCountAccountData(String month);

}
