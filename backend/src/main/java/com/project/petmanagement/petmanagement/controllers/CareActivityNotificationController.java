package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.CareActivityInfoService;
import com.project.petmanagement.petmanagement.services.CareActivityNotificationService;
import com.project.petmanagement.petmanagement.services.RecurringScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/care_activity_notification")
@RequiredArgsConstructor
public class CareActivityNotificationController {
    private final RecurringScheduleService recurringScheduleService;
    private final CareActivityInfoService careActivityInfoService;
    private final CareActivityNotificationService careActivityNotificationService;

    @GetMapping("/users")
    public ResponseEntity<Object> getCareActivityNotificationByUser() {
        List<CareActivityNotification> careActivityNotificationList = careActivityNotificationService.getCareActivityNotificationByUser();
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all notification successfully")
                .data(careActivityNotificationList)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<Object> getCareActivityNotificationByDate(@RequestParam Date date) {
        List<CareActivityNotification> careActivityNotificationList = careActivityNotificationService.getCareActivityNotificationByDate(date);
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all notification successfully")
                .data(careActivityNotificationList)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCareActivityNotificationDetails(@PathVariable("id") Long careActivityNotificationId) {
        try {
            CareActivityNotification careActivityNotification = careActivityNotificationService.getCareActivityNotificationDetails(careActivityNotificationId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get care activity notification details successfully")
                    .data(careActivityNotification)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCareActivityNotification(@Valid @RequestBody CareActivityNotificationRequest careActivityNotificationRequest) {
        try {
            CareActivityNotification careActivityNotification = careActivityNotificationService.addCareActivityNotification(careActivityNotificationRequest);
            careActivityNotification.setCareActivityInfoList(careActivityInfoService.addCareActivityInfoList(careActivityNotificationRequest.getCareActivityInfoRequestList(), careActivityNotification));
            careActivityNotification.setSchedule(recurringScheduleService.addRecurringSchedule(careActivityNotificationRequest.getRecurringScheduleRequest(), careActivityNotification));
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add care activity notification successfully")
                    .data(careActivityNotification)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCareActivityNotification(@PathVariable("id") Long careActivityNotificationId, @Valid @RequestBody CareActivityNotificationRequest careActivityNotificationRequest) {
        try {
            CareActivityNotification careActivityNotification = careActivityNotificationService.updateCareActivityNotification(careActivityNotificationId, careActivityNotificationRequest);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update care activity notification successfully")
                    .data(careActivityNotification)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCareActivityNotification(@PathVariable("id") Long careActivityNotificationId) {
        try {
            careActivityNotificationService.deleteCareActivityNotification(careActivityNotificationId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Delete care activity notification successfully")
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
