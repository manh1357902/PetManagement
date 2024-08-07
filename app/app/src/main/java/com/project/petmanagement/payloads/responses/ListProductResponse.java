package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Product;

import java.util.List;

public class ListProductResponse extends Response {
    private List<Product> data;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
