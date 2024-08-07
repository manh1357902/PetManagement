package com.project.petmanagement.models.entity;

import java.io.Serializable;
import java.util.List;

public class FoodType implements Serializable {
    private Long id;
    private String name;
    private List<NutritiousFood> nutritiousFoodList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NutritiousFood> getNutritiousFoodList() {
        return nutritiousFoodList;
    }

    public void setNutritiousFoodList(List<NutritiousFood> nutritiousFoodList) {
        this.nutritiousFoodList = nutritiousFoodList;
    }
}
