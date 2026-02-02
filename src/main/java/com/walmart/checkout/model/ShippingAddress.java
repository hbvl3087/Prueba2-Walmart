package com.walmart.checkout.model;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingAddress {
    
    @JsonProperty("street")
    @NotBlank(message = "Street is required")
    private String street;
    
    @JsonProperty("city")
    @NotBlank(message = "City is required")  
    private String city;
    
    @JsonProperty("zoneId")
    @NotBlank(message = "Zone ID is required")
    private String zoneId;
    
    // Constructor por defecto
    public ShippingAddress() {}
    
    // Constructor con parámetros
    public ShippingAddress(String street, String city, String zoneId) {
        this.street = street;
        this.city = city;
        this.zoneId = zoneId;
    }
    
    // Getters y Setters
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getZoneId() {
        return zoneId;
    }
    
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
    
    @Override
    public String toString() {
        return "ShippingAddress{street='" + street + "', city='" + city + "', zoneId='" + zoneId + "'}";
    }
}