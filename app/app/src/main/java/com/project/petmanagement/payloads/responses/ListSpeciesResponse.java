package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Species;

import java.util.List;

public class ListSpeciesResponse extends Response {
    private List<Species> data;

    public List<Species> getData() {
        return data;
    }

    public void setData(List<Species> data) {
        this.data = data;
    }
}
