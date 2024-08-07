package com.project.petmanagement.models.entity;

import java.io.Serializable;

public class CareActivityInfo implements Serializable {
    private Long id;

    private CareActivity careActivity;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CareActivity getCareActivity() {
        return careActivity;
    }

    public void setCareActivity(CareActivity careActivity) {
        this.careActivity = careActivity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
