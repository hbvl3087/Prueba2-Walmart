package com.walmart.checkout.service;

import com.walmart.checkout.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CheckoutServiceTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CheckoutService checkoutService;

    private ShoppingCart testCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Product product1 = Product.builder()
                .id("PROD001")
                .name("Test Product 1")
                .price(new BigDecimal("100.00"))
                .category("Electrónicos")
                .build();

        Product product2 = Product.builder()
                .id("PROD002")
                .name("Test Product 2")
                .price(new BigDecimal("50.00"))
                .category("Ropa")
                .build();

        CartItem item1 = CartItem.builder()
                .product(product1)
                .quantity(2)
                .build();

        CartItem item2 = CartItem.builder()
                .product(product2)
                .quantity(1)
                .build();

        testCart = ShoppingCart.builder()
                .items(Arrays.asList(item1, item2))
                //.paymentMethod(PaymentMethod.DEBIT_CARD)
                .paymentMethod(PaymentMethod.DEBIT)
                .build();
    }

    @Test
    void testProcessCheckoutCalculatesCorrectSubtotal() {
        // Dado
        when(discountService.calculateProductDiscounts(any(CartItem.class)))
                .thenReturn(Arrays.asList());
        when(discountService.calculateMinimumPurchaseDiscount(any(BigDecimal.class)))
                .thenReturn(null);
        when(discountService.calculatePaymentMethodDiscount(any(BigDecimal.class), eq(PaymentMethod.DEBIT)))
                .thenReturn(AppliedDiscount.builder()
                        .discountAmount(new BigDecimal("25.00"))
                        .build());
       when(paymentService.processPayment(any(BigDecimal.class), eq(PaymentMethod.DEBIT)))
                .thenReturn("CONFIRMED");

        // Cuando
        CheckoutResult result = checkoutService.processCheckout(testCart);

        // Entonces
        assertEquals(new BigDecimal("250.00"), result.getSubtotal()); // (100*2) + (50*1)
        assertEquals(new BigDecimal("25.00"), result.getTotalDiscounts());
        assertEquals(new BigDecimal("225.00"), result.getFinalTotal());
        assertEquals("CONFIRMED", result.getPaymentStatus());
        assertNotNull(result.getTransactionId());
    }

    @Test
    void testProcessCheckoutWithProductDiscounts() {
        // Dado
        AppliedDiscount productDiscount = AppliedDiscount.builder()
                .discountAmount(new BigDecimal("20.00"))
                .discountName("Product Discount")
                .build();

        when(discountService.calculateProductDiscounts(any(CartItem.class)))
                .thenReturn(Arrays.asList(productDiscount));
        when(discountService.calculateMinimumPurchaseDiscount(any(BigDecimal.class)))
                .thenReturn(null);
        when(discountService.calculatePaymentMethodDiscount(any(BigDecimal.class), any(PaymentMethod.class)))
                .thenReturn(null);
        when(paymentService.processPayment(any(BigDecimal.class), any(PaymentMethod.class)))
                .thenReturn("CONFIRMED");

        // Cuando
        CheckoutResult result = checkoutService.processCheckout(testCart);

        // Entonces
        assertEquals(new BigDecimal("250.00"), result.getSubtotal());
        assertTrue(result.getTotalDiscounts().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(result.getProductDiscounts());
        assertFalse(result.getProductDiscounts().isEmpty());
    }
}