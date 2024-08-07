package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.JWT.JWTTokenProvider;
import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.payloads.requests.ChangePasswordRequest;
import com.project.petmanagement.petmanagement.payloads.requests.FCMTokenRequest;
import com.project.petmanagement.petmanagement.payloads.requests.LoginRequest;
import com.project.petmanagement.petmanagement.payloads.requests.RegisterRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.payloads.responses.LoginResponse;
import com.project.petmanagement.petmanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            String jwt = tokenProvider.generateToken((JWTUserDetail) authentication.getPrincipal());
            LoginResponse loginResponse = LoginResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Login successfully")
                    .token(jwt)
                    .data(((JWTUserDetail) authentication.getPrincipal()).getUser())
                    .build();
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Please check your username or password")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        userService.register(request);
        return login(new LoginRequest(request.getPhoneNumber(), request.getPassword()));
    }

    @PostMapping(value = "/fcm")
    public ResponseEntity<Object> setFcmForUser(@RequestBody FCMTokenRequest fcmTokenRequest) {
        Boolean state = userService.setFcm(fcmTokenRequest.getFcmToken());
        if (state) {
            DataResponse response = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Set FCM token successfully")
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Cannot set FCM token. Please try again")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/change_password")
    public ResponseEntity<Object> changeUserPassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getRenewPassword())) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Retype password is not match with the new password").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            userService.changePassword(changePasswordRequest.getNewPassword());
            DataResponse response = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Password changed successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An error occurs when changing your password")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
