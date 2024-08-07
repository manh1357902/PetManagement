package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Order;

public class OrderResponse extends Response {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
