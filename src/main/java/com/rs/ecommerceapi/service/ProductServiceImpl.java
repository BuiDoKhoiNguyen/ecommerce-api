package com.rs.ecommerceapi.service;

import com.rs.ecommerceapi.exception.ProductException;
import com.rs.ecommerceapi.model.Category;
import com.rs.ecommerceapi.model.Product;
import com.rs.ecommerceapi.repository.CategoryRepository;
import com.rs.ecommerceapi.repository.ProductRepository;
import com.rs.ecommerceapi.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest createProducRequest) {
        Category topLevel = categoryRepository.findByName(createProducRequest.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(createProducRequest.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(createProducRequest.getSecondLevelCategory(), topLevel.getName());

        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(createProducRequest.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(createProducRequest.getThirdLevelCategory(), secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(createProducRequest.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(createProducRequest.getTitle());
        product.setColor(createProducRequest.getColor());
        product.setDescription(createProducRequest.getDescription());
        product.setPrice(createProducRequest.getPrice());
        product.setDiscountedPrice(createProducRequest.getDiscountedPrice());
        product.setDiscountPercent(createProducRequest.getDiscountPercent());
        product.setQuantity(createProducRequest.getQuantity());
        product.setBrand(createProducRequest.getBrand());
        product.setCategory(thirdLevel);
        product.setSizes(createProducRequest.getSize());
        product.setImageUrl(createProducRequest.getImageUrl());
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product productRequest) throws ProductException {
        Product product = findProductById(productId);
        if(productRequest.getQuantity() != 0) {
            product.setQuantity(productRequest.getQuantity());
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Product not found with id: " + productId );
    }

    @Override
    public List<Product> findProductByCategory(String category) throws ProductException {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if( !colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().allMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }
        if(stock!=null) {
            if(stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0)
                        .collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() == 0)
                        .collect(Collectors.toList());

            }
        }

        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min((startIndex + pageable.getPageSize()), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts  = new PageImpl<>(pageContent, pageable, products.size());

        return filteredProducts;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

}
