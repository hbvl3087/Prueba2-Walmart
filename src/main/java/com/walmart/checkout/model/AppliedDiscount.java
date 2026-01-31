package com.walmart.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedDiscount {
    
    private String discountId;
    private String discountName;
    private String discountType; // PRODUCT, PROMOTION, PAYMENT_METHOD
    private BigDecimal discountAmount;
    private String description;
    private String applicableItem; // product ID or "TOTAL" for payment method discounts
}