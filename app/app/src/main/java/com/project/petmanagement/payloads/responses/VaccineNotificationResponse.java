package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.VaccinationNotification;

public class VaccineNotificationResponse extends Response {
    private VaccinationNotification data;

    public VaccinationNotification getData() {
        return data;
    }

    public void setData(VaccinationNotification data) {
        this.data = data;
    }
}
