package com.project.petmanagement.payloads.responses;

import com.project.petmanagement.models.entity.User;

public class UserResponse extends Response{
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
