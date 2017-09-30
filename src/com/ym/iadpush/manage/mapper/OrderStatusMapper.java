/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.OrderStatus;

public interface OrderStatusMapper {

    List<OrderStatus> getAllOrderStatus(@Param("orderId") Integer orderId);

}
