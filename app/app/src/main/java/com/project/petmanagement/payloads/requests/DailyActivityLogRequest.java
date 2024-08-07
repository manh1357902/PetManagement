package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;

public class DailyActivityLogRequest {
    private Long id;

    private String title;

    private String date;

    private String time;

    private String image;

    private String note;

    @SerializedName("pet_id")
    private Long petId;

    @SerializedName("daily_activity_id")
    private Long dailyActivityId;

    public DailyActivityLogRequest() {
    }

    public DailyActivityLogRequest(Long id, String title, String date, String time, String image, String note, Long petId, Long dailyActivityId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.image = image;
        this.note = note;
        this.petId = petId;
        this.dailyActivityId = dailyActivityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getDailyActivityId() {
        return dailyActivityId;
    }

    public void setDailyActivityId(Long dailyActivityId) {
        this.dailyActivityId = dailyActivityId;
    }
}
