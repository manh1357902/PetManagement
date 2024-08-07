package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.CareActivity;

import java.util.List;

public class ListCareActivityResponse extends Response {
    private List<CareActivity> data;

    public List<CareActivity> getData() {
        return data;
    }

    public void setData(List<CareActivity> data) {
        this.data = data;
    }
}
