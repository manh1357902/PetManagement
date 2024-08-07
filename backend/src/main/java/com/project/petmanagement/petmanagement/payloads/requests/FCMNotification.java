package com.project.petmanagement.petmanagement.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FCMNotification {
    String fcmToken;
    String title;
    String body;
    Object data;
}
