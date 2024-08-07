package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.Disease;

import java.util.List;

public class ListDiseaseResponse extends Response {
    List<Disease> data;

    public List<Disease> getData() {
        return data;
    }

    public void setData(List<Disease> data) {
        this.data = data;
    }
}
