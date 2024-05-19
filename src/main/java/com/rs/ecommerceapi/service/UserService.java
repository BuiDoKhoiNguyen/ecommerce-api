package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.UserException;
import com.rs.ecommerceapi.model.User;


public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
