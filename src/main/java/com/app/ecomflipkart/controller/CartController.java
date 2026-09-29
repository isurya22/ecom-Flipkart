package com.app.ecomflipkart.controller;

import com.app.ecomflipkart.Dto.CartItemRequest;
import com.app.ecomflipkart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-user-ID") String userId,
                                           @RequestBody CartItemRequest request){
        if (!cartService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Product is out Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
