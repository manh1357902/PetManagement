package com.project.petmanagement.models.entity;

import com.project.petmanagement.models.enums.QualityEnum;

import java.io.Serializable;
import java.util.List;

public class NutritiousFood implements Serializable {
    private Long id;
    private String name;
    private Species species;
    private String image;
    private String description;
    private String nutrition;
    private QualityEnum quality;
    private String ingredient;
    private List<FoodType> foodTypes;

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

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

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QualityEnum getQuality() {
        return quality;
    }

    public void setQuality(QualityEnum quality) {
        this.quality = quality;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public List<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(List<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
    }
}
