package com.rs.ecommerceapi.controller;

import com.rs.ecommerceapi.exception.CartItemException;
import com.rs.ecommerceapi.exception.UserException;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.service.CartItemService;
import com.rs.ecommerceapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

import com.rs.ecommerceapi.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("{CartItemId}")
    @Operation(description = "delete item from cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Delete item")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        return new ResponseEntity<>(new ApiResponse(true, "Item removed from cart successfully"), HttpStatus.OK);
    }
}
