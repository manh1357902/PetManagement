package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.MedicalDocument;

import java.util.List;

public class ListMedicalResponse {
    private List<MedicalDocument> data;

    public List<MedicalDocument> getData() {
        return data;
    }

    public void setData(List<MedicalDocument> data) {
        this.data = data;
    }
}
