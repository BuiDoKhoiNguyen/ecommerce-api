package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.CartItemException;
import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.exception.UserException;
import com.rs.ecommerceapi.model.Cart;
import com.rs.ecommerceapi.model.CartItem;
import com.rs.ecommerceapi.model.Product;


public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
