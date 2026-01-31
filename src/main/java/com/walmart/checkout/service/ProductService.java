package com.walmart.checkout.service;

import com.walmart.checkout.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    
    private final Map<String, Product> productCatalog;
    
    public ProductService() {
        this.productCatalog = initializeProductCatalog();
    }
    
    public Optional<Product> findById(String productId) {
        return Optional.ofNullable(productCatalog.get(productId));
    }
    
    public Map<String, Product> getAllProducts() {
        return new HashMap<>(productCatalog);
    }
    
    private Map<String, Product> initializeProductCatalog() {
        Map<String, Product> catalog = new HashMap<>();
        
        // Sample products for demonstration - Updated with p-001, p-010, p-003 format
        catalog.put("p-001", Product.builder()
                .id("p-001")
                .name("Smartphone Samsung Galaxy")
                .price(new BigDecimal("809991"))
                .category("Electronics")
                .description("Latest Samsung Galaxy smartphone")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("p-010", Product.builder()
                .id("p-010")
                .name("Laptop Dell XPS 13")
                .price(new BigDecimal("1169991"))
                .category("Electronics")
                .description("High-performance ultrabook")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("p-003", Product.builder()
                .id("p-003")
                .name("Nike Air Max Sneakers")
                .price(new BigDecimal("116991"))
                .category("Footwear")
                .description("Comfortable running shoes")
                .eligibleForPromotions(true)
                .build());
                
        // Keep existing products too
        catalog.put("PROD001", Product.builder()
                .id("PROD001")
                .name("Smartphone Samsung Galaxy")
                .price(new BigDecimal("809991"))
                .category("Electronics")
                .description("Latest Samsung Galaxy smartphone")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("PROD002", Product.builder()
                .id("PROD002")
                .name("Laptop Dell XPS 13")
                .price(new BigDecimal("1169991"))
                .category("Electronics")
                .description("High-performance ultrabook")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("PROD003", Product.builder()
                .id("PROD003")
                .name("Nike Air Max Sneakers")
                .price(new BigDecimal("116991"))
                .category("Footwear")
                .description("Comfortable running shoes")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("PROD004", Product.builder()
                .id("PROD004")
                .name("Organic Coffee Beans")
                .price(new BigDecimal("22491"))
                .category("Food")
                .description("Premium organic coffee beans")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("PROD005", Product.builder()
                .id("PROD005")
                .name("Wireless Headphones")
                .price(new BigDecimal("179991"))
                .category("Electronics")
                .description("Noise-cancelling wireless headphones")
                .eligibleForPromotions(true)
                .build());
                
        return catalog;
    }
}