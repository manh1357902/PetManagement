package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.CareActivity;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.services.CareActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/care_activities")
public class CareActivityController {
    private final CareActivityService careActivityService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllCareActivities() {
        List<CareActivity> careActivities = careActivityService.getAllCareActivities();
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all care activities successfully")
                .data(careActivities)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
