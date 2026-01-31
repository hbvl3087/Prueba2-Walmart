package com.walmart.checkout.controller;

import com.walmart.checkout.model.Promotion;
import com.walmart.checkout.service.PromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@Api(value = "Promotions", description = "Promotion management APIs")
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    
    @GetMapping("/active")
    @ApiOperation(value = "Get active promotions", notes = "Retrieve all currently active promotions")
    public ResponseEntity<List<Promotion>> getActivePromotions() {
        return ResponseEntity.ok(promotionService.getActivePromotions());
    }
    
    @GetMapping("/applicable")
    @ApiOperation(value = "Get applicable promotions", notes = "Get promotions applicable to a specific product or category")
    public ResponseEntity<List<Promotion>> getApplicablePromotions(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String category) {
        
        List<Promotion> promotions = promotionService.getApplicablePromotions(productId, category);
        return ResponseEntity.ok(promotions);
    }
}