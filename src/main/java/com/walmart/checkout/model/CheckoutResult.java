package com.walmart.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResult {
    
    private String transactionId;
    private ShoppingCart cart;
    
    // Financial breakdown
    private BigDecimal subtotal;
    private List<AppliedDiscount> productDiscounts;
    private List<AppliedDiscount> promotionDiscounts;
    private AppliedDiscount paymentMethodDiscount;
    private BigDecimal totalDiscounts;
    private BigDecimal finalTotal;
    
    // Transaction details
    private PaymentMethod paymentMethod;
    private String paymentStatus; // PENDING, CONFIRMED, FAILED
    private LocalDateTime processedAt;
    
    // Summary for display
    private String summary;
}