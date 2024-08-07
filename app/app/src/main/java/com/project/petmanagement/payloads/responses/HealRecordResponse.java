package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.HealthRecord;

public class HealRecordResponse extends Response {
    private HealthRecord data;

    public HealthRecord getData() {
        return data;
    }

    public void setData(HealthRecord data) {
        this.data = data;
    }
}
