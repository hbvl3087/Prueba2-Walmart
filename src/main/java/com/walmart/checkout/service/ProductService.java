package com.walmart.checkout.service;

import com.walmart.checkout.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        Map<String, List<Product>> groupedProducts = new HashMap<>();
        
        for (Product product : productCatalog.values()) {
            String category = product.getCategory();
            groupedProducts.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
        }
        
        return groupedProducts;
    }
    
    private Map<String, Product> initializeProductCatalog() {
        Map<String, Product> catalog = new HashMap<>();
        
        // Productos de muestra para demostración - Actualizado con formato p-001, p-010, p-003
        catalog.put("p-001", Product.builder()
                .id("p-001")
                .name("Smartphone Samsung Galaxy")
                .price(new BigDecimal("809991"))
                .category("Electrónicos")
                .description("Último smartphone Samsung Galaxy")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("p-010", Product.builder()
                .id("p-010")
                .name("Laptop Dell XPS 13")
                .price(new BigDecimal("1169991"))
                .category("Electrónicos")
                .description("Ultrabook de alto rendimiento")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("p-003", Product.builder()
                .id("p-003")
                .name("Nike Air Max Sneakers")
                .price(new BigDecimal("116991"))
                .category("Calzado")
                .description("Zapatos cómodos para correr")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("PROD004", Product.builder()
                .id("PROD004")
                .name("Organic Coffee Beans")
                .price(new BigDecimal("22491"))
                .category("Alimentos")
                .description("Granos de café orgánico premium")
                .eligibleForPromotions(true)
                .build());
                
        catalog.put("PROD005", Product.builder()
                .id("PROD005")
                .name("Wireless Headphones")
                .price(new BigDecimal("179991"))
                .category("Electrónicos")
                .description("Audífonos inalámbricos con cancelación de ruido")
                .eligibleForPromotions(true)
                .build());
                
        return catalog;
    }
}