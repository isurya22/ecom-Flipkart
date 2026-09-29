package com.app.ecomflipkart.Dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
