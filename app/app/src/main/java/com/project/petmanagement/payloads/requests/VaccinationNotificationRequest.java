package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VaccinationNotificationRequest {
    @SerializedName("pet_id")
    private Long petId;

    private String title;

    @SerializedName("vaccine_id")
    private Long vaccineId;

    private String note;

    @SerializedName("schedules")
    private List<OneTimeScheduleRequest> oneTimeScheduleRequestList;

    public VaccinationNotificationRequest(Long petId, String title, Long vaccineId, String note, List<OneTimeScheduleRequest> oneTimeScheduleRequestList) {
        this.petId = petId;
        this.title = title;
        this.vaccineId = vaccineId;
        this.note = note;
        this.oneTimeScheduleRequestList = oneTimeScheduleRequestList;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OneTimeScheduleRequest> getOneTimeScheduleRequestList() {
        return oneTimeScheduleRequestList;
    }

    public void setOneTimeScheduleRequestList(List<OneTimeScheduleRequest> oneTimeScheduleRequestList) {
        this.oneTimeScheduleRequestList = oneTimeScheduleRequestList;
    }
}
