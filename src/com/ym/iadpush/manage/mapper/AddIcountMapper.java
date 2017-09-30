package com.ym.iadpush.manage.mapper;

import com.ym.iadpush.manage.entity.AddIcount;
import com.ym.iadpush.manage.entity.AddIcountExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AddIcountMapper {
	
    int countByExample(AddIcountExample example);

    int deleteByExample(AddIcountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AddIcount record);

    int insertSelective(AddIcount record);

    List<AddIcount> selectByExample(AddIcountExample example);

    AddIcount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AddIcount record, @Param("example") AddIcountExample example);

    int updateByExample(@Param("record") AddIcount record, @Param("example") AddIcountExample example);

    int updateByPrimaryKeySelective(AddIcount record);

    int updateByPrimaryKey(AddIcount record);
    
    List<AddIcount> queryByOrder(Map<String,Object> params);
    
    Integer countByOrder(Map<String,Object> params);
}