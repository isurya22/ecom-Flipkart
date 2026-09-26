package com.app.ecomflipkart.service;

import com.app.ecomflipkart.Dto.ProductRequest;
import com.app.ecomflipkart.Dto.ProductResponse;
import com.app.ecomflipkart.model.Product;
import com.app.ecomflipkart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(String.valueOf(savedProduct.getId()));
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setPrice(savedProduct.getPrice());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setActive(savedProduct.isActive());
        return response;
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingUser-> {
                    updateProductFromRequest(existingUser, productRequest);
                    Product savedProduct = productRepository.save(existingUser);
                    return mapToProductResponse(savedProduct);
                });
    }
}
