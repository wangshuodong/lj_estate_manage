/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Building;

/**
 * @Author: lixingbiao 2017-6-11 下午4:37:32
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public interface BuildingMapper {
    //删除楼栋根据参数
    Integer deleteBuildingByParams(Map<String, Object> paramMap);
    //删除楼栋
    Integer deleteBuilding(int buildingId);
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    Integer insertBuilding(Building building);

    List<Building> getBuildings(Map<String, Object> paramMap);

    List<Building> getBuildingsByHouseId(@Param("houseId") int houseId);

    Building getBuildingById(Integer id);

    Building getBuildingByName(@Param("buildingName") String buildingName, @Param("houseId") Integer houseId);

    int getBuildingsCount(Map<String, Object> paramMap);
}
