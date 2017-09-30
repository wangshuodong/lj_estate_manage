/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Location;

/**
 * @Author: lixingbiao 2017-6-11 下午4:37:32
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public interface LocationMapper {
    //删除单元根据参数
    Integer deleteLocationByParams(Map<String, Object> paramMap);
    //删除单元
    Integer deleteLocation(int locationId);
    //修改物业
    Integer changeDepartment(Map<String, Object> paramMap);
    List<Location> getLocationsByBuildingId(@Param("buildingId") int buildingId);

    Integer insertLocation(Location location);

    List<Location> getLocations(Map<String, Object> paramMap);
    
    int getLocationsCount(Map<String, Object> paramMap);

    Location getLocationById(Integer id);

    Location getLocationByName(@Param("locationName") String locationName, @Param("buildingId") Integer buildingId);
}
