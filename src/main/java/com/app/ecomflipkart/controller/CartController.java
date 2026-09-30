package com.app.ecomflipkart.controller;

import com.app.ecomflipkart.Dto.CartItemRequest;
import com.app.ecomflipkart.model.CartItem;
import com.app.ecomflipkart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeCart(@RequestHeader("X-user-ID") String userId,@PathVariable Long productId){
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();  //201 returns removed cart or 404 not found
    }

    @GetMapping("/searchCart")
    public ResponseEntity<List<CartItem>> fetchCart(@RequestHeader("X-user-ID") String userId){
        return new ResponseEntity<>(cartService.getCartItems(userId), HttpStatus.OK);
    }
}
