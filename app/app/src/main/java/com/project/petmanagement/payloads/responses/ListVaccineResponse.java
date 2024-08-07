package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Vaccine;

import java.util.List;

public class ListVaccineResponse extends Response {
    private List<Vaccine> data;

    public List<Vaccine> getData() {
        return data;
    }

    public void setData(List<Vaccine> data) {
        this.data = data;
    }
}
