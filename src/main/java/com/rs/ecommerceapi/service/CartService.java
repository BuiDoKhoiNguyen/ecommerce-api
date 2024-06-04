package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Cart;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest addItemRequest) throws ProductException;
    public Cart findUserCart(Long userId);
}
