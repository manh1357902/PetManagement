package com.project.petmanagement.models.entity;

import java.util.Date;

public class DailyActivityLog {
    private Long id;
    private String title;
    private Date date;
    private String time;
    private String image;
    private String note;
    private Pet pet;
    private DailyActivity dailyActivity;

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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public DailyActivity getDailyActivity() {
        return dailyActivity;
    }

    public void setDailyActivity(DailyActivity dailyActivity) {
        this.dailyActivity = dailyActivity;
    }
}
