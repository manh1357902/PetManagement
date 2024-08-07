package com.project.petmanagement.payloads.requests;

import okhttp3.MultipartBody;

public class MedicalDocumentRequest {
    private String title;
    private String note;
    private Long petId;

    public MedicalDocumentRequest() {
    }

    public MedicalDocumentRequest(String title, String note, Long petId, MultipartBody.Part file) {
        this.title = title;
        this.note = note;
        this.petId = petId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
