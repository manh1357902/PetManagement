package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.CareActivityNotification;

public class CareActivityNotificationResponse extends Response {
    private CareActivityNotification data;

    public CareActivityNotification getData() {
        return data;
    }

    public void setData(CareActivityNotification data) {
        this.data = data;
    }
}
