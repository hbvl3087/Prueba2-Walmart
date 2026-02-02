package com.walmart.checkout.service;

import com.walmart.checkout.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {
    
    @Autowired
    private PromotionService promotionService;
    
    public List<AppliedDiscount> calculateProductDiscounts(CartItem cartItem) {
        List<AppliedDiscount> discounts = new ArrayList<>();
        Product product = cartItem.getProduct();
        
        if (!product.isEligibleForPromotions()) {
            return discounts;
        }
        
        List<Promotion> applicablePromotions = promotionService
                .getApplicablePromotions(product.getId(), product.getCategory());
        
        BigDecimal itemSubtotal = cartItem.calculateSubtotal();
        
        for (Promotion promotion : applicablePromotions) {
            BigDecimal discountAmount = calculatePromotionDiscount(promotion, cartItem, itemSubtotal);
            
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                discounts.add(AppliedDiscount.builder()
                        .discountId(promotion.getPromotionId())
                        .discountName(promotion.getName())
                        .discountType("PROMOTION")
                        .discountAmount(discountAmount)
                        .description(promotion.getDescription())
                        .applicableItem(product.getId())
                        .build());
            }
        }
        
        return discounts;
    }
    
    public AppliedDiscount calculatePaymentMethodDiscount(BigDecimal subtotal, PaymentMethod paymentMethod) {
        if (paymentMethod.getDiscountPercentage() <= 0) {
            return null;
        }
        
        BigDecimal discountAmount = subtotal
                .multiply(BigDecimal.valueOf(paymentMethod.getDiscountPercentage()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        
        return AppliedDiscount.builder()
                .discountId("PAYMENT_" + paymentMethod.name())
                .discountName(paymentMethod.getDisplayName() + " Discount")
                .discountType("PAYMENT_METHOD")
                .discountAmount(discountAmount)
                .description(paymentMethod.getDiscountPercentage() + "% discount for " + paymentMethod.getDisplayName())
                .applicableItem("TOTAL")
                .build();
    }
    
    public AppliedDiscount calculateMinimumPurchaseDiscount(BigDecimal subtotal) {
        List<Promotion> minimumPurchasePromotions = promotionService.getActivePromotions().stream()
                .filter(p -> p.getType() == Promotion.PromotionType.MINIMUM_PURCHASE)
                .filter(p -> subtotal.compareTo(p.getMinimumPurchase()) >= 0)
                .collect(Collectors.toList());
        
        // Aplicar la mejor promoción de compra mínima
        return minimumPurchasePromotions.stream()
                .max((p1, p2) -> p1.getFixedDiscountAmount().compareTo(p2.getFixedDiscountAmount()))
                .map(promotion -> AppliedDiscount.builder()
                        .discountId(promotion.getPromotionId())
                        .discountName(promotion.getName())
                        .discountType("PROMOTION")
                        .discountAmount(promotion.getFixedDiscountAmount())
                        .description(promotion.getDescription())
                        .applicableItem("TOTAL")
                        .build())
                .orElse(null);
    }
    
    private BigDecimal calculatePromotionDiscount(Promotion promotion, CartItem cartItem, BigDecimal itemSubtotal) {
        switch (promotion.getType()) {
            case PERCENTAGE_DISCOUNT:
                return itemSubtotal
                        .multiply(promotion.getDiscountPercentage())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                
            case FIXED_AMOUNT_DISCOUNT:
                return promotion.getFixedDiscountAmount();
                
            case BUY_X_GET_Y_FREE:
                return calculateBuyXGetYDiscount(promotion, cartItem);
                
            case MINIMUM_PURCHASE:
                // Los descuentos de compra mínima se manejan por separado a nivel de carrito
                return BigDecimal.ZERO;
                
            default:
                return BigDecimal.ZERO;
        }
    }
    
    private BigDecimal calculateBuyXGetYDiscount(Promotion promotion, CartItem cartItem) {
        Integer quantity = cartItem.getQuantity();
        Integer requiredQuantity = promotion.getRequiredQuantity();
        Integer freeQuantity = promotion.getFreeQuantity();
        
        if (quantity < requiredQuantity) {
            return BigDecimal.ZERO;
        }
        
        // Calcular cuántos artículos gratis obtiene el cliente
        int eligibleSets = quantity / requiredQuantity;
        int totalFreeItems = Math.min(eligibleSets * freeQuantity, quantity);
        
        return cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(totalFreeItems));
    }
}