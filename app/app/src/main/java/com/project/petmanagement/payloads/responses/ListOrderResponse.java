package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Order;

import java.util.List;

public class ListOrderResponse extends Response {
    private List<Order> data;

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
