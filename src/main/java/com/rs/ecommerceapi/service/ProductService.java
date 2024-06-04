package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.request.CreateProductRequest;
import com.rs.ecommerceapi.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest createProducRequest);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product productRequest) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;

    public List<Product> findProductByCategory(String category) throws ProductException;

    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice
            , Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    public List<Product> findAllProducts();
}
