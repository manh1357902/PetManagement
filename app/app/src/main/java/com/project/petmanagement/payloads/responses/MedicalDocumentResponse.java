package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.MedicalDocument;

public class MedicalDocumentResponse extends Response {
    private MedicalDocument data;

    public MedicalDocument getData() {
        return data;
    }

    public void setData(MedicalDocument data) {
        this.data = data;
    }
}
