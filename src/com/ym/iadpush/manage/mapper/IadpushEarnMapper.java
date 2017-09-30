package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.IadpushEarn;
import com.ym.iadpush.manage.entity.IadpushEarnExample;

public interface IadpushEarnMapper {
    int countByExample(IadpushEarnExample example);

    int deleteByExample(IadpushEarnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IadpushEarn record);

    int insertSelective(IadpushEarn record);

    List<IadpushEarn> selectByExample(IadpushEarnExample example);

    IadpushEarn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IadpushEarn record, @Param("example") IadpushEarnExample example);

    int updateByExample(@Param("record") IadpushEarn record, @Param("example") IadpushEarnExample example);

    int updateByPrimaryKeySelective(IadpushEarn record);

    int updateByPrimaryKey(IadpushEarn record);
    
    Double sumEarnByDate(Map<String,Object> params);
    
    IadpushEarn selectByParams(Map<String,Object> params);
    
    int updateEarnByParams(Map<String,Object> params);
    
    
}