package com.project.petmanagement.models.entity;

import java.io.Serializable;
import java.util.List;

public class Disease implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String symptoms;
    private List<Prevention> preventions;
    private List<Treatment> treatments;
    private Species species;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public List<Prevention> getPreventions() {
        return preventions;
    }

    public void setPreventions(List<Prevention> preventions) {
        this.preventions = preventions;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}