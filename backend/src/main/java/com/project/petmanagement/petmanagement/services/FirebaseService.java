package com.project.petmanagement.petmanagement.services;

import com.google.cloud.storage.BlobId;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.project.petmanagement.petmanagement.payloads.requests.FCMNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService {
    @Value("${app.bucket-name}")
    private String bucketName;

    public void uploadFile(String fileName, byte[] fileBytes) {
        StorageClient storageClient = StorageClient.getInstance(FirebaseApp.getInstance());
        InputStream inputStream = new ByteArrayInputStream(fileBytes);
        storageClient.bucket().create(fileName, inputStream, "application/octet-stream");
    }

    public String getDownloadUrl(String fileName) {
        long signUrlDuration = 3600;
        return StorageClient.getInstance().bucket(bucketName).get(fileName).signUrl(signUrlDuration, TimeUnit.SECONDS).toString();
    }

    public Boolean deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        return StorageClient.getInstance().bucket(bucketName).get(fileName).getStorage().delete(blobId);
    }

    public String pushNotification(FCMNotification pnsRequest) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(pnsRequest.getTitle())
                        .setBody(pnsRequest.getBody())
                        .build())
                .setToken(pnsRequest.getFcmToken())
                .build();
        return FirebaseMessaging.getInstance().send(message);
    }
}
