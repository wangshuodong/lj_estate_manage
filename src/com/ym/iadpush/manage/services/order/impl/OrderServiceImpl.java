/**
 * 
 */
package com.ym.iadpush.manage.services.order.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.DateUtils;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.common.utils.DecimalFormatUtils;
import com.ym.iadpush.manage.entity.Order;
import com.ym.iadpush.manage.entity.OrderItem;
import com.ym.iadpush.manage.entity.OrderStatus;
import com.ym.iadpush.manage.entity.Order_old;
import com.ym.iadpush.manage.entity.WeekData;
import com.ym.iadpush.manage.mapper.OrderItemMapper;
import com.ym.iadpush.manage.mapper.OrderMapper;
import com.ym.iadpush.manage.mapper.OrderStatusMapper;
import com.ym.iadpush.manage.mapper.Order_oldMapper;
import com.ym.iadpush.manage.services.order.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private Order_oldMapper order_oldMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public Integer inserOrder(Order order) {
        Integer inserOrder = orderMapper.inserOrder(order);
        return inserOrder;
    }

    @Override
    public List<Order> getAllOrders(Map<String, Object> paramMap) {
        List<Order> allOrders = orderMapper.getAllOrders(paramMap);
        return allOrders;
    }

    @Override
    public Integer inserOrderStatus(Map<String, Object> paramMap) {
        Integer inserOrderStatus = orderMapper.inserOrderStatus(paramMap);
        return inserOrderStatus;
    }

    @Override
    public Order getOrderById(Integer orderId, String departmentCode) {
        Order order = orderMapper.getOrderById(orderId);
        return order;
    }

    @Override
    public Integer updateOrder(Integer status, Integer orderId) {
        Integer updateOrderStatus = orderMapper.updateOrder(status, orderId);
        return updateOrderStatus;
    }

    @Override
    public Integer updateOrderBill(Order order, Integer orderId) {
        Integer updateOrder = orderMapper.updateOrderBill(order, orderId);
        return updateOrder;
    }

    @Override
    public List<OrderStatus> getAllOrderStatus(Integer orderId) {
        List<OrderStatus> allOrders = orderStatusMapper.getAllOrderStatus(orderId);
        return allOrders;
    }

    @Override
    public Integer sumOrderByDate(Map<String, Object> params) {
        return orderMapper.sumOrderByDate(params);
    }

    @Override
    public WeekData queryWeekData(String startDate, String endDate, String departmentCode, String companyId,
            Integer djsaleId) {
        return orderMapper.queryWeekData(startDate, endDate, departmentCode, companyId, djsaleId);
    }

    @Override
    public Integer getOrderCount(Map<String, Object> paramMap) {
        Integer in = orderMapper.getOrderCount(paramMap);
        return in;
    }

    @Override
    public Integer deleteOrder(Integer orderId) {
        Integer in = orderMapper.deleteOrder(orderId);
        return in;
    }

    @Override
    public QueryResult getOrderCollect(Map<String, Object> paramMap) {
        QueryResult result = new QueryResult();

        result.setCollect(orderMapper.getOrderCollect(paramMap));

        return result;
    }

    @Override
    public String getNextOrderCode(String pre) {

        String date = DateUtils.formartRandomDate(new Date(), "yyyyMMdd");

        Order order = getMaxOrderCode(pre + "" + date);

        String billCode = "";

        if (order != null) {
            billCode = order.getCode();
        }

        if (billCode != null && !billCode.equals("")) {
            int seq = Integer.parseInt(billCode.substring(billCode.length() - 4, billCode.length())) + 1;
            date += DecimalFormatUtils.format("0000", seq);
        } else {
            date += "0001";
        }

        return pre + "" + date;
    }

    @Override
    public Order getMaxOrderCode(String date) {
        Order order = orderMapper.getMaxOrderCode(date);
        return order;
    }

    @Override
    public Integer inserOrderItem(OrderItem orderItem) {
        return orderItemMapper.inserOrderItem(orderItem);
    }

    @Override
    public Integer inserOrder_old(Order_old order_old) {
        return order_oldMapper.inserOrder_old(order_old);
    }
}
