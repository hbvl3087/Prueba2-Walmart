package com.walmart.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    
    private String promotionId;
    private String name;
    private String description;
    private PromotionType type;
    
    // Discount configuration
    private BigDecimal discountPercentage;
    private BigDecimal fixedDiscountAmount;
    private BigDecimal minimumPurchase;
    
    // Applicable products/categories
    private List<String> applicableProductIds;
    private List<String> applicableCategories;
    
    // Quantity-based promotions
    private Integer requiredQuantity;
    private Integer freeQuantity; // For buy X get Y free
    
    private boolean active = true;
    
    public enum PromotionType {
        PERCENTAGE_DISCOUNT,    // 20% off
        FIXED_AMOUNT_DISCOUNT, // $9000 CLP off
        BUY_X_GET_Y_FREE,      // Buy 2 get 1 free
        MINIMUM_PURCHASE       // $13500 CLP off on purchases over $90000 CLP
    }
}