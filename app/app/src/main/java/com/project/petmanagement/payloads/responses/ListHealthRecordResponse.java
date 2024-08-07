package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.HealthRecord;

import java.util.List;

public class ListHealthRecordResponse extends Response {
    private List<HealthRecord> data;

    public List<HealthRecord> getData() {
        return data;
    }

    public void setData(List<HealthRecord> data) {
        this.data = data;
    }
}
