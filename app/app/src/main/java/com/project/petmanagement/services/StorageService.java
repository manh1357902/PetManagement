package com.project.petmanagement.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.project.petmanagement.models.entity.User;

public class StorageService {
    private static final String MY_SHARED_PREFERENCE = "petmanagement";
    private SharedPreferences sharedPreferences;

    public StorageService(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public void setUser(String key, User user) {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, jsonUser);
        editor.apply();
    }

    public User getUser(String key) {
        String strUser = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(strUser, User.class);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
