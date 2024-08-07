package com.project.petmanagement.models.entity;

import java.io.Serializable;

public class Vaccine implements Serializable {
    private Long id;
    private String name;
    private Integer vaccineDose;
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

    public Integer getVaccineDose() {
        return vaccineDose;
    }

    public void setVaccineDose(Integer vaccineDose) {
        this.vaccineDose = vaccineDose;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
