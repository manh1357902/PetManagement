package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {
    @SerializedName("old_password")
    String oldPassword;
    @SerializedName("new_password")
    String newPassword;
    @SerializedName("renew_password")
    String retypeNewPassword;

    public ChangePasswordRequest() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRetypeNewPassword() {
        return retypeNewPassword;
    }

    public void setRetypeNewPassword(String retypeNewPassword) {
        this.retypeNewPassword = retypeNewPassword;
    }
}
