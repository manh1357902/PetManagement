package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.OneTimeSchedule;

import java.util.List;

public class ListOneTimeScheduleResponse extends Response {
    private List<OneTimeSchedule> data;

    public List<OneTimeSchedule> getData() {
        return data;
    }

    public void setData(List<OneTimeSchedule> data) {
        this.data = data;
    }
}
