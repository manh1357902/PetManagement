package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.Product;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    private ResponseEntity<Object> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        DataResponse productResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all products successfully")
                .data(productList)
                .build();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductDetails(@PathVariable("id") Long productId) {
        try {
            Product product = productService.getProductDetails(productId);
            DataResponse productResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get product details successfully")
                    .data(product)
                    .build();
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}