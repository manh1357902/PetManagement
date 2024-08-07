package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.DailyActivity;

import java.util.List;

public class ListDaiLyActivityResponse extends Response {
    private List<DailyActivity> data;

    public List<DailyActivity> getData() {
        return data;
    }

    public void setData(List<DailyActivity> data) {
        this.data = data;
    }
}
