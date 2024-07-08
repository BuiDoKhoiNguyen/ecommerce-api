package com.rs.ecommerceapi.service.impl;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.model.Rating;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.repository.RatingRepository;
import com.rs.ecommerceapi.request.RatingRequest;
import com.rs.ecommerceapi.service.ProductService;
import com.rs.ecommerceapi.service.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
private final RatingRepository ratingRepository;
    private final ProductService productService;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest ratingRequest, User user) throws ProductException {
        Product product = productService.findProductById(ratingRequest.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setRating(ratingRequest.getRating());
        rating.setUser(user);
        rating.setCreatedAt(LocalDateTime.now() );
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
