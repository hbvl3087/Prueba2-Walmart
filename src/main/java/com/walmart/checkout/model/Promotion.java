package com.walmart.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    
    private String promotionId;
    private String name;
    private String description;
    private PromotionType type;
    
    // Configuración de descuento
    private BigDecimal discountPercentage;
    private BigDecimal fixedDiscountAmount;
    private BigDecimal minimumPurchase;
    
    // Productos/categorías aplicables
    private List<String> applicableProductIds;
    private List<String> applicableCategories;
    
    // Promociones basadas en cantidad
    private Integer requiredQuantity;
    private Integer freeQuantity; // Para compra X obtén Y gratis
    
    private boolean active = true;
    
    public enum PromotionType {
        PERCENTAGE_DISCOUNT,    // 20% de descuento
        FIXED_AMOUNT_DISCOUNT, // $9000 CLP de descuento
        BUY_X_GET_Y_FREE,      // Compra 2 obtén 1 gratis
        MINIMUM_PURCHASE       // $13500 CLP de descuento en compras superiores a $90000 CLP
    }
}