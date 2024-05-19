package com.rs.ecommerceapi.request;

import com.rs.ecommerceapi.model.Size;

import java.util.HashSet;
import java.util.Set;

public class CreateProducRequest {
    private String title;
    private String description;
    private int price;

    private  int discountedPrice;

    private int discountPercent;

    private int quantity;
    private String brand;
    private String color;

    private Set<Size> size = new HashSet<>();

    private String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
}
