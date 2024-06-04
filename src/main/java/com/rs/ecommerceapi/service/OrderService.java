package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.OrderException;
import com.rs.ecommerceapi.model.Address;
import com.rs.ecommerceapi.model.Order;
import com.rs.ecommerceapi.model.User;

import java.util.List;

public interface OrderService {
    public Order findOrderById(Long orderId) throws OrderException;

    public Order createOrder(User user, Address ShippingAddress);

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order cancelOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
