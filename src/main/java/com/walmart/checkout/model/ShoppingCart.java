package com.walmart.checkout.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class ShoppingCart {
    
    private String cartId;
    
    @NotEmpty(message = "Cart must contain at least one item")
    @Valid
    private List<CartItem> items;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    @Valid
    private ShippingAddress shippingAddress;
    
    private BigDecimal subtotal;
    private BigDecimal totalDiscounts;
    private BigDecimal finalTotal;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Applied promotions and discounts for transparency
    private List<AppliedDiscount> appliedDiscounts;
}