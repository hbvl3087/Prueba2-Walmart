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
        
        AppliedDiscount result = discountService.calculatePaymentMethodDiscount(subtotal, PaymentMethod.DEBIT);
        
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
        // Dado
        Product product = Product.builder()
                .id("PROD001")
                .name("Test Product")
                .price(new BigDecimal("100.00"))
                .category("Electrónicos")
                .eligibleForPromotions(true)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(1)
                .build();

        Promotion promotion = Promotion.builder()
                .promotionId("PROMO001")
                .name("20% Descuento Electrónicos")
                .type(Promotion.PromotionType.PERCENTAGE_DISCOUNT)
                .discountPercentage(new BigDecimal("20"))
                .build();

        when(promotionService.getApplicablePromotions("PROD001", "Electrónicos"))
                .thenReturn(Arrays.asList(promotion));

        // Cuando
        List<AppliedDiscount> discounts = discountService.calculateProductDiscounts(cartItem);

        // Entonces
        assertEquals(1, discounts.size());
        AppliedDiscount discount = discounts.get(0);
        assertEquals(new BigDecimal("20.00"), discount.getDiscountAmount());
        assertEquals("PROMOTION", discount.getDiscountType());
        assertEquals("20% Descuento Electrónicos", discount.getDiscountName());
    }

    @Test
    void testCalculateMinimumPurchaseDiscount() {
        // Dado
        BigDecimal subtotal = new BigDecimal("135000");

        Promotion promotion = Promotion.builder()
                .promotionId("PROMO003")
                .name("$9000 CLP off over $90000 CLP")
                .type(Promotion.PromotionType.MINIMUM_PURCHASE)
                .fixedDiscountAmount(new BigDecimal("9000"))
                .minimumPurchase(new BigDecimal("90000"))
                .active(true)
                .build();

        when(promotionService.getActivePromotions())
                .thenReturn(Arrays.asList(promotion));

        // Cuando
        AppliedDiscount discount = discountService.calculateMinimumPurchaseDiscount(subtotal);

        // Entonces
        assertNotNull(discount);
        assertEquals(new BigDecimal("9000"), discount.getDiscountAmount());
        assertEquals("PROMOTION", discount.getDiscountType());
    }

    @Test
    void testCalculateMinimumPurchaseDiscountBelowThreshold() {
        // Dado
        BigDecimal subtotal = new BigDecimal("45000");

        Promotion promotion = Promotion.builder()
                .promotionId("PROMO003")
                .name("$9000 CLP off over $90000 CLP")
                .type(Promotion.PromotionType.MINIMUM_PURCHASE)
                .fixedDiscountAmount(new BigDecimal("9000"))
                .minimumPurchase(new BigDecimal("90000"))
                .active(true)
                .build();

        when(promotionService.getActivePromotions())
                .thenReturn(Arrays.asList(promotion));

        // Cuando
        AppliedDiscount discount = discountService.calculateMinimumPurchaseDiscount(subtotal);

        // Entonces
        assertNull(discount);
    }
}