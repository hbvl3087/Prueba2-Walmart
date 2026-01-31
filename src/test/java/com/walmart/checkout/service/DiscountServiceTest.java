package com.walmart.checkout.service;

import com.walmart.checkout.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DiscountServiceTest {

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatePaymentMethodDiscount() {
        BigDecimal subtotal = new BigDecimal("100.00");
        
        AppliedDiscount result = discountService.calculatePaymentMethodDiscount(subtotal, PaymentMethod.DEBIT_CARD);
        
        assertNotNull(result);
        assertEquals(new BigDecimal("10.00"), result.getDiscountAmount());
        assertEquals("PAYMENT_METHOD", result.getDiscountType());
        assertTrue(result.getDiscountName().contains("Debit Card"));
    }

    @Test
    void testCalculatePaymentMethodDiscountNoDiscount() {
        BigDecimal subtotal = new BigDecimal("100.00");
        
        AppliedDiscount result = discountService.calculatePaymentMethodDiscount(subtotal, PaymentMethod.CREDIT_CARD);
        
        assertNull(result);
    }

    @Test
    void testCalculateProductDiscountsWithPercentagePromotion() {
        // Given
        Product product = Product.builder()
                .id("PROD001")
                .name("Test Product")
                .price(new BigDecimal("100.00"))
                .category("Electronics")
                .eligibleForPromotions(true)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(1)
                .build();

        Promotion promotion = Promotion.builder()
                .promotionId("PROMO001")
                .name("20% Off Electronics")
                .type(Promotion.PromotionType.PERCENTAGE_DISCOUNT)
                .discountPercentage(new BigDecimal("20"))
                .build();

        when(promotionService.getApplicablePromotions("PROD001", "Electronics"))
                .thenReturn(Arrays.asList(promotion));

        // When
        List<AppliedDiscount> discounts = discountService.calculateProductDiscounts(cartItem);

        // Then
        assertEquals(1, discounts.size());
        AppliedDiscount discount = discounts.get(0);
        assertEquals(new BigDecimal("20.00"), discount.getDiscountAmount());
        assertEquals("PROMOTION", discount.getDiscountType());
        assertEquals("20% Off Electronics", discount.getDiscountName());
    }

    @Test
    void testCalculateMinimumPurchaseDiscount() {
        // Given
        BigDecimal subtotal = new BigDecimal("150.00");

        Promotion promotion = Promotion.builder()
                .promotionId("PROMO003")
                .name("$10 off over $100")
                .type(Promotion.PromotionType.MINIMUM_PURCHASE)
                .fixedDiscountAmount(new BigDecimal("10.00"))
                .minimumPurchase(new BigDecimal("100.00"))
                .active(true)
                .build();

        when(promotionService.getActivePromotions())
                .thenReturn(Arrays.asList(promotion));

        // When
        AppliedDiscount discount = discountService.calculateMinimumPurchaseDiscount(subtotal);

        // Then
        assertNotNull(discount);
        assertEquals(new BigDecimal("10.00"), discount.getDiscountAmount());
        assertEquals("PROMOTION", discount.getDiscountType());
    }

    @Test
    void testCalculateMinimumPurchaseDiscountBelowThreshold() {
        // Given
        BigDecimal subtotal = new BigDecimal("50.00");

        Promotion promotion = Promotion.builder()
                .promotionId("PROMO003")
                .name("$10 off over $100")
                .type(Promotion.PromotionType.MINIMUM_PURCHASE)
                .fixedDiscountAmount(new BigDecimal("10.00"))
                .minimumPurchase(new BigDecimal("100.00"))
                .active(true)
                .build();

        when(promotionService.getActivePromotions())
                .thenReturn(Arrays.asList(promotion));

        // When
        AppliedDiscount discount = discountService.calculateMinimumPurchaseDiscount(subtotal);

        // Then
        assertNull(discount);
    }
}