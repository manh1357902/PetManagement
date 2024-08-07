package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;

public class HealRecordRequest {
    @SerializedName(("checkup_date"))
    private String checkUpDate;
    private Double weight;
    @SerializedName("exercise_level")
    private Integer exerciseLevel;
    private String symptoms;
    private String diagnosis;
    private String note;
    @SerializedName("pet_id")
    private Long petId;

    public HealRecordRequest(String checkUpDate, Double weight, Integer exerciseLevel, String symptoms, String diagnosis, String note, Long petId) {
        this.checkUpDate = checkUpDate;
        this.weight = weight;
        this.exerciseLevel = exerciseLevel;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.note = note;
        this.petId = petId;
    }

    public String getCheckUpDate() {
        return checkUpDate;
    }

    public void setCheckUpDate(String checkUpDate) {
        this.checkUpDate = checkUpDate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getExerciseLevel() {
        return exerciseLevel;
    }

    public void setExerciseLevel(Integer exerciseLevel) {
        this.exerciseLevel = exerciseLevel;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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
