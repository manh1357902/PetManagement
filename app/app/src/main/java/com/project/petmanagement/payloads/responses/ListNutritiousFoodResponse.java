package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.NutritiousFood;

import java.util.List;

public class ListNutritiousFoodResponse extends Response {
    private List<NutritiousFood> data;

    public List<NutritiousFood> getData() {
        return data;
    }

    public void setData(List<NutritiousFood> data) {
        this.data = data;
    }
}
