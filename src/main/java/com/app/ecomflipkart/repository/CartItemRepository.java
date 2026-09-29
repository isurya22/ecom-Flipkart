package com.app.ecomflipkart.repository;

import com.app.ecomflipkart.model.CartItem;
import com.app.ecomflipkart.model.Product;
import com.app.ecomflipkart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

}
