/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Order;
import com.ym.iadpush.manage.entity.OrderDataStatement;
import com.ym.iadpush.manage.entity.WeekData;


public interface OrderMapper {
    Integer inserOrder(Order order);

    List<Order> getAllOrders(Map<String, Object> paramMap);

    Integer deleteOrder(Integer orderId);

    Integer inserOrderStatus(Map<String, Object> paramMap);

    Order getOrderById(Integer orderId);

    Integer updateOrder(@Param("status") Integer status, @Param("orderId") Integer orderId);

    Integer updateOrderBill(@Param("order") Order order, @Param("orderId") Integer orderId);

    Integer getOrderCount(Map<String, Object> paramMap);

    Integer sumOrderByDate(Map<String, Object> params);

    WeekData queryWeekData(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("departmentCode") String departmentCode, @Param("companyId") String companyId,
            @Param("djsaleId") Integer djsaleId);

    OrderDataStatement getOrderCollect(Map<String, Object> paramMap);

    Order getMaxOrderCode(@Param("date") String date);
}
