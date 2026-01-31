package com.walmart.checkout.service;

import com.walmart.checkout.model.Promotion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {
    
    private final List<Promotion> activePromotions;
    
    public PromotionService() {
        this.activePromotions = initializePromotions();
    }
    
    public List<Promotion> getActivePromotions() {
        return activePromotions.stream()
                .filter(Promotion::isActive)
                .collect(Collectors.toList());
    }
    
    public List<Promotion> getApplicablePromotions(String productId, String category) {
        return getActivePromotions().stream()
                .filter(promotion -> isPromotionApplicable(promotion, productId, category))
                .collect(Collectors.toList());
    }
    
    private boolean isPromotionApplicable(Promotion promotion, String productId, String category) {
        // Check if promotion applies to specific product
        if (promotion.getApplicableProductIds() != null && 
            promotion.getApplicableProductIds().contains(productId)) {
            return true;
        }
        
        // Check if promotion applies to product category
        if (promotion.getApplicableCategories() != null && 
            promotion.getApplicableCategories().contains(category)) {
            return true;
        }
        
        // If no specific products or categories defined, promotion applies to all
        return (promotion.getApplicableProductIds() == null || promotion.getApplicableProductIds().isEmpty()) &&
               (promotion.getApplicableCategories() == null || promotion.getApplicableCategories().isEmpty());
    }
    
    private List<Promotion> initializePromotions() {
        return Arrays.asList(
            // 20% off Electronics
            Promotion.builder()
                .promotionId("PROMO001")
                .name("Electronics Sale")
                .description("20% off all Electronics")
                .type(Promotion.PromotionType.PERCENTAGE_DISCOUNT)
                .discountPercentage(new BigDecimal("20"))
                .applicableCategories(Arrays.asList("Electronics"))
                .active(true)
                .build(),
                
            // Buy 2 get 1 free on specific product
            Promotion.builder()
                .promotionId("PROMO002")
                .name("Coffee Special")
                .description("Buy 2 Coffee get 1 free")
                .type(Promotion.PromotionType.BUY_X_GET_Y_FREE)
                .requiredQuantity(2)
                .freeQuantity(1)
                .applicableProductIds(Arrays.asList("PROD004"))
                .active(true)
                .build(),
                
            // $10 off on purchases over $100
            Promotion.builder()
                .promotionId("PROMO003")
                .name("Minimum Purchase Discount")
                .description("$10 off on purchases over $100")
                .type(Promotion.PromotionType.MINIMUM_PURCHASE)
                .fixedDiscountAmount(new BigDecimal("10"))
                .minimumPurchase(new BigDecimal("100"))
                .active(true)
                .build(),
                
            // Fixed $15 discount on Footwear
            Promotion.builder()
                .promotionId("PROMO004")
                .name("Footwear Discount")
                .description("$15 off all Footwear")
                .type(Promotion.PromotionType.FIXED_AMOUNT_DISCOUNT)
                .fixedDiscountAmount(new BigDecimal("15"))
                .applicableCategories(Arrays.asList("Footwear"))
                .active(true)
                .build()
        );
    }
}