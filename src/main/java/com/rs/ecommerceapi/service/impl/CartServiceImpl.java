package com.rs.ecommerceapi.service.impl;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Cart;
import com.rs.ecommerceapi.model.CartItem;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.repository.CartRepository;
import com.rs.ecommerceapi.request.AddItemRequest;
import com.rs.ecommerceapi.service.CartItemService;
import com.rs.ecommerceapi.service.CartService;
import com.rs.ecommerceapi.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest addItemRequest) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(addItemRequest.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, addItemRequest.getSize(), userId);

        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(addItemRequest.getQuantity());
            cartItem.setUserId(userId);
            int price = (int) (product.getDiscountedPrice() * addItemRequest.getQuantity());
            cartItem.setPrice(price);
            cartItem.setSize(addItemRequest.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().equals(createdCartItem);
        }
        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItems = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += totalPrice + cartItem.getPrice();
            totalDiscountedPrice += totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItems += cartItem.getQuantity();

        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItems);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
