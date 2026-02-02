package com.walmart.checkout.service;

import com.walmart.checkout.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {
    
    @Autowired
    private DiscountService discountService;
    
    @Autowired
    private PaymentService paymentService;
    
    public CheckoutResult processCheckout(ShoppingCart cart) {
        // Generar ID de transacción
        String transactionId = UUID.randomUUID().toString();
        
        // Calcular subtotal
        BigDecimal subtotal = calculateSubtotal(cart);
        
        // Aplicar descuentos de producto y promoción
        List<AppliedDiscount> allDiscounts = new ArrayList<>();
        List<AppliedDiscount> productDiscounts = new ArrayList<>();
        
        for (CartItem item : cart.getItems()) {
            List<AppliedDiscount> itemDiscounts = discountService.calculateProductDiscounts(item);
            allDiscounts.addAll(itemDiscounts);
            productDiscounts.addAll(itemDiscounts);
            
            // Actualizar item del carrito con información de descuento
            BigDecimal itemDiscount = itemDiscounts.stream()
                    .map(AppliedDiscount::getDiscountAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
            item.setItemSubtotal(item.calculateSubtotal());
            item.setItemDiscount(itemDiscount);
            item.setItemTotal(item.getItemSubtotal().subtract(itemDiscount));
        }
        
        // Aplicar descuento de compra mínima (promoción a nivel de carrito)
        AppliedDiscount minimumPurchaseDiscount = discountService.calculateMinimumPurchaseDiscount(subtotal);
        List<AppliedDiscount> promotionDiscounts = new ArrayList<>();
        if (minimumPurchaseDiscount != null) {
            allDiscounts.add(minimumPurchaseDiscount);
            promotionDiscounts.add(minimumPurchaseDiscount);
        }
        
        // Aplicar descuento de método de pago
        AppliedDiscount paymentMethodDiscount = discountService.calculatePaymentMethodDiscount(subtotal, cart.getPaymentMethod());
        if (paymentMethodDiscount != null) {
            allDiscounts.add(paymentMethodDiscount);
        }
        
        // Calcular totales
        BigDecimal totalDiscounts = allDiscounts.stream()
                .map(AppliedDiscount::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        BigDecimal finalTotal = subtotal.subtract(totalDiscounts);
        
        // Actualizar carrito con valores calculados
        cart.setSubtotal(subtotal);
        cart.setTotalDiscounts(totalDiscounts);
        cart.setFinalTotal(finalTotal);
        cart.setAppliedDiscounts(allDiscounts);
        cart.setUpdatedAt(LocalDateTime.now());
        
        // Procesar pago (simulado)
        String paymentStatus = paymentService.processPayment(finalTotal, cart.getPaymentMethod());
        
        // Construir resultado del checkout
        CheckoutResult result = CheckoutResult.builder()
                .transactionId(transactionId)
                .cart(cart)
                .subtotal(subtotal)
                .productDiscounts(productDiscounts)
                .promotionDiscounts(promotionDiscounts)
                .paymentMethodDiscount(paymentMethodDiscount)
                .totalDiscounts(totalDiscounts)
                .finalTotal(finalTotal)
                .paymentMethod(cart.getPaymentMethod())
                .paymentStatus(paymentStatus)
                .processedAt(LocalDateTime.now())
                .summary(generateSummary(subtotal, allDiscounts, finalTotal))
                .build();
        
        return result;
    }
    
    private BigDecimal calculateSubtotal(ShoppingCart cart) {
        return cart.getItems().stream()
                .map(CartItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private String generateSummary(BigDecimal subtotal, List<AppliedDiscount> discounts, BigDecimal finalTotal) {
        StringBuilder summary = new StringBuilder();
        summary.append("Checkout Summary:\n");
        summary.append(String.format("Subtotal: $%.0f CLP\n", subtotal));
        
        if (!discounts.isEmpty()) {
            summary.append("Discounts Applied:\n");
            for (AppliedDiscount discount : discounts) {
                summary.append(String.format("  - %s: -$%.0f CLP\n", discount.getDiscountName(), discount.getDiscountAmount()));
            }
            summary.append(String.format("Total Discounts: -$%.0f CLP\n", 
                discounts.stream().map(AppliedDiscount::getDiscountAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
        }
        
        summary.append(String.format("Final Total: $%.0f CLP", finalTotal));
        return summary.toString();
    }
}