/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Housing;

/**
 * @Author: lixingbiao 2017-6-11 下午4:37:32
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public interface HousingMapper {
    //删除小区根据参数
    Integer deleteHousingByParams(Map<String, Object> paramMap);
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    
    Integer deleteHousing(int housingId);
    Integer addSmsCount(Map<String, Object> paramMap);
    Integer subtractSmsCount(int housingId);
    Integer insertHousing(Housing housing);

    List<Housing> getHousings(Map<String, Object> paramMap);

    List<Housing> getAllHousings();

    Housing getHousingById(Integer id);

    Integer updateHousing(@Param("housing") Housing housing, @Param("housingId") Integer housingId);

    Housing getHousingByName(String houseingName);
    
    int getHousingsCount(Map<String, Object> paramMap);
}
