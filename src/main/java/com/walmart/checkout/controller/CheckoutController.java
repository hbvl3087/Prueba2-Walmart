package com.walmart.checkout.controller;

import com.walmart.checkout.model.*;
import com.walmart.checkout.service.CheckoutService;
import com.walmart.checkout.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/checkout")
@Api(value = "Checkout", description = "Checkout and payment processing APIs")
public class CheckoutController {
    
    @Autowired
    private CheckoutService checkoutService;
    
    @Autowired
    private ProductService productService;
    
    @PostMapping("/process")
    @ApiOperation(value = "Process checkout", notes = "Process shopping cart checkout with discounts and payment")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Checkout processed successfully"),
        @ApiResponse(code = 400, message = "Invalid cart data"),
        @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<?> processCheckout(@Valid @RequestBody ShoppingCartRequest cartRequest) {
        try {
            // Debug logging
            System.out.println("Received request: " + cartRequest);
            if (cartRequest.getItems() != null) {
                for (int i = 0; i < cartRequest.getItems().size(); i++) {
                    CartItemRequest item = cartRequest.getItems().get(i);
                    System.out.println("Item " + i + ": sku=" + item.getSku() + ", quantity=" + item.getQuantity());
                }
            }
            
            // Convert ShoppingCartRequest to ShoppingCart
            ShoppingCart cart = convertToShoppingCart(cartRequest);
            
            // Set cart metadata
            if (cart.getCartId() == null || cart.getCartId().isEmpty()) {
                cart.setCartId(UUID.randomUUID().toString());
            }
            
            CheckoutResult result = checkoutService.processCheckout(cart);
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing checkout: " + e.getMessage());
        }
    }
    
    @PostMapping("/process-legacy")
    @ApiOperation(value = "Process checkout (Legacy format)", notes = "Process shopping cart checkout with legacy format for web UI compatibility")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Checkout processed successfully"),
        @ApiResponse(code = 400, message = "Invalid cart data"),
        @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<?> processCheckoutLegacy(@Valid @RequestBody ShoppingCart cart) {
        try {
            // Set cart metadata
            if (cart.getCartId() == null || cart.getCartId().isEmpty()) {
                cart.setCartId(UUID.randomUUID().toString());
            }
            
            // Validate products exist and ensure complete product data
            for (CartItem item : cart.getItems()) {
                String productId = item.getProduct().getId();
                if (productService.findById(productId).isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body("Product not found: " + productId);
                }
                
                // Ensure we have the complete product data
                Product fullProduct = productService.findById(productId).get();
                item.setProduct(fullProduct);
            }
            
            CheckoutResult result = checkoutService.processCheckout(cart);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing checkout: " + e.getMessage());
        }
    }
    
    private ShoppingCart convertToShoppingCart(ShoppingCartRequest request) {
        List<CartItem> cartItems = new ArrayList<>();
        
        // Convert each CartItemRequest to CartItem
        for (CartItemRequest itemRequest : request.getItems()) {
            // Find product by SKU
            Product product = productService.findById(itemRequest.getSku())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with SKU: " + itemRequest.getSku()));
            
            // Create CartItem with full product data
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();
                    
            cartItems.add(cartItem);
        }
        
        return ShoppingCart.builder()
                .cartId(request.getCartId())
                .items(cartItems)
                .paymentMethod(request.getPaymentMethod())
                .shippingAddress(request.getShippingAddress())
                .build();
    }
    
    @GetMapping("/payment-methods")
    @ApiOperation(value = "Get available payment methods", notes = "Retrieve all available payment methods with their discount percentages")
    public ResponseEntity<List<Map<String, Object>>> getPaymentMethods() {
        List<Map<String, Object>> methods = new ArrayList<>();
        
        for (PaymentMethod method : PaymentMethod.values()) {
            Map<String, Object> methodInfo = new HashMap<>();
            methodInfo.put("name", method.name());
            methodInfo.put("displayName", method.getDisplayName());
            methodInfo.put("discountPercentage", method.getDiscountPercentage());
            methods.add(methodInfo);
        }
        
        return ResponseEntity.ok(methods);
    }
    
    @GetMapping("/products")
    @ApiOperation(value = "Get available products", notes = "Retrieve all products available for purchase")
    public ResponseEntity<Map<String, Product>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        response.put("message", "Validation failed");
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }
}