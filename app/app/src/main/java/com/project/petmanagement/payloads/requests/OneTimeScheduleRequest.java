package com.project.petmanagement.payloads.requests;

import java.io.Serializable;

public class OneTimeScheduleRequest implements Serializable {
    private Long id;

    private String date;

    private String time;

    private Boolean status;

    public OneTimeScheduleRequest() {

    }

    public OneTimeScheduleRequest(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
