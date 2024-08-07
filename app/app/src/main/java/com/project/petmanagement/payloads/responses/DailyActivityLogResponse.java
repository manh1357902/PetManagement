package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.DailyActivityLog;

public class DailyActivityLogResponse extends Response {
    private DailyActivityLog data;

    public DailyActivityLog getData() {
        return data;
    }

    public void setData(DailyActivityLog data) {
        this.data = data;
    }
}
