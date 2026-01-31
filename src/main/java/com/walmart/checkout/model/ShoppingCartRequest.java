package com.walmart.checkout.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShoppingCartRequest {
    
    @JsonProperty("cartId")
    private String cartId;
    
    @JsonProperty("items")
    @NotEmpty(message = "Cart must contain at least one item")
    @Valid
    private List<CartItemRequest> items;
    
    @JsonProperty("shippingAddress")
    @Valid
    private ShippingAddress shippingAddress;
    
    @JsonProperty("paymentMethod")
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    // Default constructor
    public ShoppingCartRequest() {}
    
    // Constructor with parameters
    public ShoppingCartRequest(String cartId, List<CartItemRequest> items, ShippingAddress shippingAddress, PaymentMethod paymentMethod) {
        this.cartId = cartId;
        this.items = items;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and Setters
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    
    public List<CartItemRequest> getItems() {
        return items;
    }
    
    public void setItems(List<CartItemRequest> items) {
        this.items = items;
    }
    
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    @Override
    public String toString() {
        return "ShoppingCartRequest{cartId='" + cartId + "', items=" + items + ", shippingAddress=" + shippingAddress + ", paymentMethod=" + paymentMethod + "}";
    }
}