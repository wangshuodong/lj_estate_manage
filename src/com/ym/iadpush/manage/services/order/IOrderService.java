/**
 * 
 */
package com.ym.iadpush.manage.services.order;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Order;
import com.ym.iadpush.manage.entity.OrderItem;
import com.ym.iadpush.manage.entity.OrderStatus;
import com.ym.iadpush.manage.entity.Order_old;
import com.ym.iadpush.manage.entity.WeekData;


public interface IOrderService {
    Integer inserOrder(Order order);
    
    Integer inserOrderItem(OrderItem  orderItem);

    Order getOrderById(Integer orderId, String departmentCode);

    Integer deleteOrder(Integer orderId);

    List<OrderStatus> getAllOrderStatus(Integer orderId);

    List<Order> getAllOrders(Map<String, Object> paramMap);

    Integer inserOrderStatus(Map<String, Object> paramMap);

    Integer updateOrder(Integer status, Integer orderId);

    Integer updateOrderBill(Order order, Integer orderId);

    Integer getOrderCount(Map<String, Object> paramMap);

    Integer sumOrderByDate(Map<String, Object> params);

    WeekData queryWeekData(String startDate, String endDate, String departmentCode, String companyId, Integer djsaleId);

    QueryResult getOrderCollect(Map<String, Object> paramMap);

    Order getMaxOrderCode(String date);

    String getNextOrderCode(String pre);
    
    Integer inserOrder_old(Order_old order_old);

}
