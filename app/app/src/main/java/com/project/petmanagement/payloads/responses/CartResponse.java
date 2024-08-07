package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Cart;

public class CartResponse extends Response {
    private Cart data;

    public Cart getData() {
        return data;
    }

    public void setData(Cart data) {
        this.data = data;
    }
}
