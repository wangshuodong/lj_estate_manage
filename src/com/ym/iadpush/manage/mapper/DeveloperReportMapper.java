package com.ym.iadpush.manage.mapper;

import com.ym.iadpush.manage.entity.DeveloperReport;
import com.ym.iadpush.manage.entity.DeveloperReportExample;
import com.ym.iadpush.manage.entity.DeveloperReportKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeveloperReportMapper {
    int countByExample(DeveloperReportExample example);

    int deleteByExample(DeveloperReportExample example);

    int deleteByPrimaryKey(DeveloperReportKey key);

    int insert(DeveloperReport record);

    int insertSelective(DeveloperReport record);

    List<DeveloperReport> selectByExample(DeveloperReportExample example);

    DeveloperReport selectByPrimaryKey(DeveloperReportKey key);

    int updateByExampleSelective(@Param("record") DeveloperReport record, @Param("example") DeveloperReportExample example);

    int updateByExample(@Param("record") DeveloperReport record, @Param("example") DeveloperReportExample example);

    int updateByPrimaryKeySelective(DeveloperReport record);

    int updateByPrimaryKey(DeveloperReport record);
    
}