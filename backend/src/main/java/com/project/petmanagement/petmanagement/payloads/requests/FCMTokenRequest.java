package com.project.petmanagement.petmanagement.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMTokenRequest {
    String fcmToken;
}
