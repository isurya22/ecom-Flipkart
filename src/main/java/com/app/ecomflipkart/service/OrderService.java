package com.app.ecomflipkart.service;

import com.app.ecomflipkart.Dto.OrderItemDTO;
import com.app.ecomflipkart.Dto.OrderResponse;
import com.app.ecomflipkart.model.*;
import com.app.ecomflipkart.repository.OrderRepository;
import com.app.ecomflipkart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {

        //Validate for cart items
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()){
           return Optional.empty();
        }

        //Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
           return Optional.empty();
        }
        User user = userOptional.get();

        //calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        /* CartItems should convert to new OrderItems */
        List<OrderItem> orderItems = cartItems.stream()
                .map(item-> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        //clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }

}
