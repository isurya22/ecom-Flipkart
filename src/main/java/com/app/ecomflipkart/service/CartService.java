package com.app.ecomflipkart.service;

import com.app.ecomflipkart.Dto.CartItemRequest;
import com.app.ecomflipkart.model.CartItem;
import com.app.ecomflipkart.model.Product;
import com.app.ecomflipkart.model.User;
import com.app.ecomflipkart.repository.CartItemRepository;
import com.app.ecomflipkart.repository.ProductRepository;
import com.app.ecomflipkart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
       //look for product is exist or not
       Optional<Product> productOut = productRepository.findById(request.getProductId());
       if(productOut.isEmpty()){
           return false;
       }
       //Stock checking
       Product product = productOut.get();
       if (product.getStockQuantity() < request.getQuantity()){
           return false;
       }

       //User is exist or not
       Optional<User> userOut = userRepository.findById(Long.valueOf(userId));
       if (userOut.isEmpty()){
           return false;
       }

       //Stock, Product, User are exist
       User user = userOut.get();

       //Exist Product is in cart or not
       CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

       if(existingCartItem != null){
           //Update the quantity
           existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
           existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
           cartItemRepository.save(existingCartItem);
       } else {
           // Create a new Item
           CartItem cartItem = new CartItem();
           cartItem.setUser(user);
           cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
           cartItem.setQuantity(request.getQuantity());
           cartItem.setProduct(product);
           cartItemRepository.save(cartItem);
       }
       return true;
    }
}
