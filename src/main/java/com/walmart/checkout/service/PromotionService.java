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
        // Verificar si la promoción aplica a producto específico
        if (promotion.getApplicableProductIds() != null && 
            promotion.getApplicableProductIds().contains(productId)) {
            return true;
        }
        
        // Verificar si la promoción aplica a categoría del producto
        if (promotion.getApplicableCategories() != null && 
            promotion.getApplicableCategories().contains(category)) {
            return true;
        }
        
        // Si no hay productos o categorías específicas definidas, la promoción aplica a todos
        return (promotion.getApplicableProductIds() == null || promotion.getApplicableProductIds().isEmpty()) &&
               (promotion.getApplicableCategories() == null || promotion.getApplicableCategories().isEmpty());
    }
    
    private List<Promotion> initializePromotions() {
        return Arrays.asList(
            // 20% de descuento en Electrónicos
            Promotion.builder()
                .promotionId("PROMO001")
                .name("Oferta Electrónicos")
                .description("20% de descuento en todos los Electrónicos")
                .type(Promotion.PromotionType.PERCENTAGE_DISCOUNT)
                .discountPercentage(new BigDecimal("20"))
                .applicableCategories(Arrays.asList("Electrónicos"))
                .active(true)
                .build(),
                
            // Compra 2 obtén 1 gratis en producto específico
            Promotion.builder()
                .promotionId("PROMO002")
                .name("Oferta Especial Café")
                .description("Compra 2 Café y obtén 1 gratis")
                .type(Promotion.PromotionType.BUY_X_GET_Y_FREE)
                .requiredQuantity(2)
                .freeQuantity(1)
                .applicableProductIds(Arrays.asList("PROD004"))
                .active(true)
                .build(),
                
            // $9000 CLP de descuento en compras superiores a $90000 CLP
            Promotion.builder()
                .promotionId("PROMO003")
                .name("Descuento Compra Mínima")
                .description("$9000 CLP de descuento en compras superiores a $90000 CLP")
                .type(Promotion.PromotionType.MINIMUM_PURCHASE)
                .fixedDiscountAmount(new BigDecimal("9000"))
                .minimumPurchase(new BigDecimal("90000"))
                .active(true)
                .build(),
                
            // Descuento fijo de $13500 CLP en Calzado
            Promotion.builder()
                .promotionId("PROMO004")
                .name("Descuento Calzado")
                .description("$13500 CLP de descuento en todo el Calzado")
                .type(Promotion.PromotionType.FIXED_AMOUNT_DISCOUNT)
                .fixedDiscountAmount(new BigDecimal("13500"))
                .applicableCategories(Arrays.asList("Calzado"))
                .active(true)
                .build()
        );
    }
}