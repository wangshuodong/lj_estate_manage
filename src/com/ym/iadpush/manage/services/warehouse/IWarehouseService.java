/**
 * 
 */
package com.ym.iadpush.manage.services.warehouse;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.Warehouse;


public interface IWarehouseService {
    
    Integer insertWarehouse(Warehouse warehouse);
    
    Integer deleteWarehouse(Integer id);
    Integer updateWarehouse(Warehouse warehouse,Integer id);
    
    
    Warehouse getWarehouseById(Integer id);

    List<Warehouse> getAllWarehouses(Map<String, Object> paramMap);
    
    List<Warehouse> getAllWarehousesByCompanyId(Integer companyId);

}
