package com.rs.ecommerceapi.service.impl;

import com.rs.ecommerceapi.model.OrderItem;
import com.rs.ecommerceapi.repository.OrderItemRepository;
import com.rs.ecommerceapi.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
