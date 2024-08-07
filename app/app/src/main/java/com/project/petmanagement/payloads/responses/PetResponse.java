package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Pet;

public class PetResponse extends Response {
    private Pet data;

    public Pet getData() {
        return data;
    }

    public void setData(Pet data) {
        this.data = data;
    }
}
