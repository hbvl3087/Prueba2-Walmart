package com.walmart.checkout.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemRequest {
    
    @JsonProperty("sku")
    @NotBlank(message = "SKU is required")
    private String sku;
    
    @JsonProperty("quantity")
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    // Constructor por defecto
    public CartItemRequest() {}
    
    // Constructor con parámetros
    public CartItemRequest(String sku, int quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }
    
    // Getters y Setters
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "CartItemRequest{sku='" + sku + "', quantity=" + quantity + "}";
    }
}