package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Vet;

import java.util.List;

public class ListVetResponse extends Response {
    private List<Vet> data;

    public List<Vet> getData() {
        return data;
    }

    public void setData(List<Vet> data) {
        this.data = data;
    }
}
