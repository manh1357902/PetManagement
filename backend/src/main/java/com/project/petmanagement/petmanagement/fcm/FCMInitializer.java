package com.project.petmanagement.petmanagement.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FCMInitializer {
    @Value("${app.firebase-config}")
    private String fcmConfig;

    @Value("${app.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void initialize() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(fcmConfig).getInputStream()))
                .setStorageBucket(bucketName)
                .build();

        FirebaseApp.initializeApp(options);
    }
}
