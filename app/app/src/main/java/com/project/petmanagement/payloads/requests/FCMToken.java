package com.project.petmanagement.payloads.requests;

public class FCMToken {
    private String fcmToken;

    public FCMToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
