/**
 * 
 */
package com.ym.iadpush.manage.services.warehouse.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.Warehouse;
import com.ym.iadpush.manage.mapper.WarehouseMapper;
import com.ym.iadpush.manage.services.warehouse.IWarehouseService;

@Service
public class WarehouseServiceImpl implements IWarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public Integer insertWarehouse(Warehouse warehouse) {
        return warehouseMapper.insertWarehouse(warehouse);
    }

    @Override
    public Warehouse getWarehouseById(Integer id) {
        return warehouseMapper.getWarehouseById(id);
    }

    @Override
    public List<Warehouse> getAllWarehouses(Map<String, Object> paramMap) {
        return warehouseMapper.getAllWarehouses(paramMap);
    }

    @Override
    public List<Warehouse> getAllWarehousesByCompanyId(Integer companyId) {
        return warehouseMapper.getAllWarehousesByCompanyId(companyId);
    }

    @Override
    public Integer deleteWarehouse(Integer id) {
        return warehouseMapper.deleteWarehouse(id);
    }

 
    @Override
    public Integer updateWarehouse(Warehouse warehouse, Integer id) {
        return warehouseMapper.updateWarehouse(warehouse, id);
    }
}
