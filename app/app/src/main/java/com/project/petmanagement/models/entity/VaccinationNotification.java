package com.project.petmanagement.models.entity;

import java.io.Serializable;
import java.util.List;

public class VaccinationNotification extends Notification implements Serializable {
    private Vaccine vaccine;
    private List<OneTimeSchedule> schedules;

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public List<OneTimeSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<OneTimeSchedule> schedules) {
        this.schedules = schedules;
    }
}
