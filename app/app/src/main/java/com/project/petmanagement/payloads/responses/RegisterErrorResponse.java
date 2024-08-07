package com.project.petmanagement.payloads.responses;

import com.google.gson.annotations.SerializedName;

public class RegisterErrorResponse {
    private int status;
    private Message message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public class Message {
        @SerializedName("fullName")
        private String fullNameError;
        @SerializedName("password")
        private String passwordError;

        @SerializedName("phoneNumber")
        private String phoneNumberError;

        @SerializedName("email")
        private String emailError;

        public String getFullNameError() {
            return fullNameError;
        }

        public void setFullNameError(String fullNameError) {
            this.fullNameError = fullNameError;
        }

        public String getPasswordError() {
            return passwordError;
        }

        public void setPasswordError(String passwordError) {
            this.passwordError = passwordError;
        }

        public String getPhoneNumberError() {
            return phoneNumberError;
        }

        public void setPhoneNumberError(String phoneNumberError) {
            this.phoneNumberError = phoneNumberError;
        }

        public String getEmailError() {
            return emailError;
        }

        public void setEmailError(String emailError) {
            this.emailError = emailError;
        }
    }
}
