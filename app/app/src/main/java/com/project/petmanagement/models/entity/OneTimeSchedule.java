package com.project.petmanagement.models.entity;

import java.io.Serializable;
import java.util.Date;

public class OneTimeSchedule implements Serializable {

    private Long id;
    private VaccinationNotification vaccinationNotification;
    private Date date;
    private String time;
    private Boolean vaccinationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VaccinationNotification getVaccinationNotification() {
        return vaccinationNotification;
    }

    public void setVaccinationNotification(VaccinationNotification vaccinationNotification) {
        this.vaccinationNotification = vaccinationNotification;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(Boolean vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }
}
