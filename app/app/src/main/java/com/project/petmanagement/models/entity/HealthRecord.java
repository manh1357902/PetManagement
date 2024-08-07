package com.project.petmanagement.models.entity;

import java.io.Serializable;
import java.util.Date;

public class HealthRecord implements Serializable {
    private Long id;
    private Date checkUpDate;
    private Double weight;
    private Integer exerciseLevel;
    private Date lastVisit;
    private String symptoms;
    private String diagnosis;
    private String treatment;
    private String note;
    private Pet pet;

    public HealthRecord(Date checkUpDate, Double weight) {
        this.checkUpDate = checkUpDate;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckUpDate() {
        return checkUpDate;
    }

    public void setCheckUpDate(Date checkUpDate) {
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

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
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

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
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
}
