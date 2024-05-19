package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.request.CreateProducRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProducRequest createProducRequest);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product product) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;

    public List<Product> findProductByCategory(String category) throws ProductException;

    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice
            , Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
}
