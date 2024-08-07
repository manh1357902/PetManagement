package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Pet;

import java.util.List;

public class ListPetResponse extends Response {
    private List<Pet> data;

    public List<Pet> getData() {
        return data;
    }

    public void setData(List<Pet> data) {
        this.data = data;
    }
}
