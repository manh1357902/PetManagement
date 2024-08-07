package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Category;

import java.util.List;

public class ListCategoryResponse extends Response {
    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
