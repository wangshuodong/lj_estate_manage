/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.StockMonth;
import com.ym.iadpush.manage.entity.StockMonthDataStatement;


public interface StockMonthMapper {

    StockMonth getStockMonth(@Param("productId") Integer productId, @Param("warehouseId") Integer warehouseId,
            @Param("month") String month);

    StockMonth getStockMonthByProductAndWarehouse(@Param("productId") Integer productId,
            @Param("warehouseId") Integer warehouseId);

    Integer insertStockMonth(StockMonth stockMonth);

    Integer updateStockMonth(@Param("stockMonth") StockMonth stockMonth, @Param("id") Integer id);

    List<StockMonth> queryStockMonthList(Map<String, Object> paramMap);

    Integer queryStockMonthCount(Map<String, Object> paramMap);

    StockMonthDataStatement queryStockMonthCollect(Map<String, Object> paramMap);
}
