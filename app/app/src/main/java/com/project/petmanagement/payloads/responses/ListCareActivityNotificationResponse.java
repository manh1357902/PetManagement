package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.CareActivityNotification;

import java.util.List;

public class ListCareActivityNotificationResponse extends Response {
    private List<CareActivityNotification> data;

    public List<CareActivityNotification> getData() {
        return data;
    }

    public void setData(List<CareActivityNotification> data) {
        this.data = data;
    }
}
