package com.walmart.checkout.model;

public enum PaymentMethod {
    CREDIT_CARD("Tarjeta de Crédito", 0.0),
    DEBIT("Tarjeta de Débito", 10.0), // Agregado por compatibilidad con tu ejemplo
    CASH("Efectivo", 5.0),
    DIGITAL_WALLET("Billetera Digital", 3.0),
    BANK_TRANSFER("Transferencia Bancaria", 7.0);
    
    private final String displayName;
    private final double discountPercentage;
    
    PaymentMethod(String displayName, double discountPercentage) {
        this.displayName = displayName;
        this.discountPercentage = discountPercentage;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public double getDiscountPercentage() {
        return discountPercentage;
    }
}
