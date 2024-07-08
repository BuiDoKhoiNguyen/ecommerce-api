package com.rs.ecommerceapi.service.impl;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.model.Review;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.repository.ProductRepository;
import com.rs.ecommerceapi.repository.ReviewRepository;
import com.rs.ecommerceapi.request.ReviewRequest;
import com.rs.ecommerceapi.service.ProductService;
import com.rs.ecommerceapi.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private ProductService productService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product = productService.findProductById(reviewRequest.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(reviewRequest.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
