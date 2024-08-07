package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;


public class UserRequest {
    @SerializedName("full_name")
    private String fullName;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    private String email;

    private String address;

    private String avatar;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
