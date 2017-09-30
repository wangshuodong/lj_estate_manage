/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Warehouse;

public interface WarehouseMapper {
    Integer insertWarehouse(Warehouse warehouse);

    Warehouse getWarehouseById(Integer id);

    List<Warehouse> getAllWarehouses(Map<String, Object> paramMap);

    List<Warehouse> getAllWarehousesByCompanyId(Integer companyId);

    Integer deleteWarehouse(@Param("id") Integer id);

    Integer updateWarehouse(@Param("warehouse") Warehouse warehouse, @Param("id") Integer id);

}
