package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.CartItemException;
import com.rs.ecommerceapi.exception.UserException;
import com.rs.ecommerceapi.model.Cart;
import com.rs.ecommerceapi.model.CartItem;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.repository.CartItemRepository;
import com.rs.ecommerceapi.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice((int)cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice((int)cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice((int) (item.getQuantity()*item.getProduct().getPrice()));
            item.setDiscountedPrice((int) (item.getProduct().getDiscountedPrice()*item.getQuantity()));
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());

        User requestUser = userService.findUserById(userId);

        if(user.getId().equals(requestUser.getId())) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new UserException("You can't remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("Cart item not found with id: "+cartItemId);
    }
}
