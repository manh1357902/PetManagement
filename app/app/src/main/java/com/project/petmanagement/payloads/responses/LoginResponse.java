package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.User;

public class LoginResponse extends Response {
    private String token;
    private User data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
