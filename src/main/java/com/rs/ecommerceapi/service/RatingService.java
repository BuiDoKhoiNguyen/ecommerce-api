package com.rs.ecommerceapi.service;


import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Rating;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest ratingRequest, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);


}
