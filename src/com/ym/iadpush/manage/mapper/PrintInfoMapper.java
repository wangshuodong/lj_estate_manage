package com.ym.iadpush.manage.mapper;

import java.util.List;

import com.ym.iadpush.manage.entity.PrintInfo;

public interface PrintInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PrintInfo record);

    int insertSelective(PrintInfo record);

    PrintInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PrintInfo record);

    int updateByPrimaryKey(PrintInfo record);
    
    PrintInfo selectBydepartmentId(Integer departmentId);
    
    List<PrintInfo> selectBystatus(Integer status);
}