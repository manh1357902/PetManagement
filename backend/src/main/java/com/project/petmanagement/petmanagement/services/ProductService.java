package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Product;
import com.project.petmanagement.petmanagement.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByIsActiveTrue();
    }

    public Product getProductDetails(Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new DataNotFoundException("Can not find product with ID: " + productId));
    }
}