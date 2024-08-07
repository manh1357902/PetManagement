package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.FoodType;

import java.util.List;

public class ListFoodTypeResponse extends Response {
    private List<FoodType> data;

    public List<FoodType> getData() {
        return data;
    }

    public void setData(List<FoodType> data) {
        this.data = data;
    }
}
