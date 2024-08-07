package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.DailyActivityLog;

import java.util.List;

public class ListDailyActivityLogResponse extends Response {
    private List<DailyActivityLog> data;

    public List<DailyActivityLog> getData() {
        return data;
    }

    public void setData(List<DailyActivityLog> data) {
        this.data = data;
    }
}
