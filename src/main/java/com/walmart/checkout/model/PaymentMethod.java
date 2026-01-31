package com.walmart.checkout.model;

public enum PaymentMethod {
    CREDIT_CARD("Credit Card", 0.0),
    //DEBIT_CARD("Debit Card", 10.0),
    DEBIT("Debit Card", 10.0), // Added for compatibility with your example
    CASH("Cash", 5.0),
    DIGITAL_WALLET("Digital Wallet", 3.0),
    BANK_TRANSFER("Bank Transfer", 7.0);
    
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