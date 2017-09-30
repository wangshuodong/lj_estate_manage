package com.ym.iadpush.manage.mapper;

import com.ym.iadpush.manage.entity.IadpushCpacount;

public interface IadpushCpacountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IadpushCpacount record);

    int insertSelective(IadpushCpacount record);

    IadpushCpacount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IadpushCpacount record);

    int updateByPrimaryKey(IadpushCpacount record);
}