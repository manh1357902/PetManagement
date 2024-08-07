package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.VaccinationNotification;

import java.util.List;

public class ListVaccineNotification extends Response {
    private List<VaccinationNotification> data;

    public List<VaccinationNotification> getData() {
        return data;
    }

    public void setData(List<VaccinationNotification> data) {
        this.data = data;
    }
}
