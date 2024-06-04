package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Review;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
