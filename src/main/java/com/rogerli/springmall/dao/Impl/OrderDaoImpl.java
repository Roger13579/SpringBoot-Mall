package com.rogerli.springmall.dao.Impl;

import com.rogerli.springmall.dao.OrderDao;
import com.rogerli.springmall.dto.OrderQueryParams;
import com.rogerli.springmall.entity.OrderItem;
import com.rogerli.springmall.entity.Orders;
import com.rogerli.springmall.repository.OrderItemJpaDao;
import com.rogerli.springmall.repository.OrderJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderJpaDao orderJpaDao;

    @Autowired
    private OrderItemJpaDao orderItemJpaDao;

    @Override
    public List<Orders> getOrders(OrderQueryParams orderQueryParams) {
        return orderJpaDao.findByUserIdAndIscartOrderByCreatedDateDesc(orderQueryParams.getUserId(), orderQueryParams.isIscart());
    }

    @Override
    public Integer createOrder(Orders orders) {
        Orders createdOrder = orderJpaDao.save(orders);
        return createdOrder.getOrderId();
    }

    @Override
    public void createOrderItem(List<OrderItem> orderItemList) {
        orderItemJpaDao.saveAll(orderItemList);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderJpaDao.deleteById(orderId);
    }

    @Override
    public Orders getOrderByOrderId(Integer orderId) {
        List<Orders> ordersList = orderJpaDao.findByOrderId(orderId);
        if (!ordersList.isEmpty()) {
            return ordersList.getFirst();
        }else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        return orderItemJpaDao.findOrderItemByOrderIdJoinProduct(orderId);
    }
    @Override
    public OrderItem getOrderItemByOrderId(Integer orderId) {
        return orderItemJpaDao.findOrderItemByOrderId(orderId);
    }
    @Override
    public void updateOrderItem(OrderItem orderItem) {
        orderItemJpaDao.save(orderItem);
    }
    @Override
    public Orders updateOrderByOrderId(Orders orders) {
        return orderJpaDao.save(orders);
    }

}
