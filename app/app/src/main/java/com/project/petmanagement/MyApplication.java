package com.project.petmanagement;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.project.petmanagement.activity.login.LoginActivity;
import com.project.petmanagement.models.entity.User;
import com.project.petmanagement.services.StorageService;

public class MyApplication extends Application {
    private static StorageService storageService;
    public static final String CHANNEL_ID = "notification_id";

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        storageService = new StorageService(getApplicationContext());
        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static StorageService getStorageService() {
        return storageService;
    }
}
