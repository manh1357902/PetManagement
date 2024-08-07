package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;

public class PetRequest {
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("breed_id")
    private Long breedId;
    private Integer gender;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    private String description;
    private String image;
    @SerializedName("is_neutered")
    private Boolean isNeutered;

    public PetRequest(String fullName, Long breedId, Integer gender, String dateOfBirth, String image, Boolean isNeutered) {
        this.fullName = fullName;
        this.breedId = breedId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.isNeutered = isNeutered;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getNeutered() {
        return isNeutered;
    }

    public void setNeutered(Boolean neutered) {
        isNeutered = neutered;
    }
}
