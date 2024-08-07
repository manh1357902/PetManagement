package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.CareActivityInfo;
import com.project.petmanagement.petmanagement.payloads.requests.CareActivityInfoRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.CareActivityInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/care_activity_info")
@RequiredArgsConstructor
public class CareActivityInfoController {
    private final CareActivityInfoService careActivityInfoService;

    @PutMapping("/update/{care_activity_notification_id}")
    public ResponseEntity<Object> updateCareActivityInfo(@PathVariable("care_activity_notification_id") Long careActivityNotificationId, @Valid @RequestBody List<CareActivityInfoRequest> careActivityInfoRequestList) {
        try {
            List<CareActivityInfo> careActivityInfoList = careActivityInfoService.updateCareActivityInfoList(careActivityNotificationId, careActivityInfoRequestList);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update care activity information successfully")
                    .data(careActivityInfoList)
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
    public ResponseEntity<Object> deleteCareActivityInfo(@PathVariable("id") Long careActivityInfoId) {
        try {
            careActivityInfoService.deleteCareActivityInfo(careActivityInfoId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Delete care activity information successfully")
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
