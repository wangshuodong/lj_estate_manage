/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.StockDetail;
import com.ym.iadpush.manage.entity.StockDetailDataStatement;


public interface StockDetailMapper {
    StockDetail getStockDetail(@Param("productId") Integer productId, @Param("warehouseId") Integer warehouseId,
            @Param("addate") Date addate);

    Integer insertStockDetail(StockDetail stockDetail);

    Integer updateStockDetail(@Param("stockDetail") StockDetail stockDetail, @Param("id") Integer id);

    public StockDetail getLatelyStockDetail(@Param("productId") Integer productId,
            @Param("warehouseId") Integer warehouseId, @Param("addate") Date addate);

    List<StockDetail> queryStockDetailList(Map<String, Object> paramMap);

    Integer queryStockDetailCount(Map<String, Object> paramMap);

    StockDetailDataStatement queryStockDetailCollect(Map<String, Object> paramMap);

}
