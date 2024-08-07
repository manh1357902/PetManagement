package com.project.petmanagement.models.entity;

import java.io.Serializable;
import java.util.List;

public class Species implements Serializable {
    private Long id;
    private String name;
    private List<Breed> breeds;

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

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }
}
