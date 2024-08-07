package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.OneTimeSchedule;
import com.project.petmanagement.petmanagement.payloads.requests.OneTimeScheduleRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.OneTimeScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/one_time_schedules")
@RequiredArgsConstructor
public class OneTimeScheduleController {
    private final OneTimeScheduleService oneTimeScheduleService;

    @PutMapping("/update/{vaccination_notification_id}")
    public ResponseEntity<Object> updateOneTimeSchedule(@PathVariable("vaccination_notification_id") Long vaccinationNotificationId, @Valid @RequestBody List<OneTimeScheduleRequest> oneTimeScheduleRequestList) {
        try {
            List<OneTimeSchedule> oneTimeScheduleList = oneTimeScheduleService.updateOneTimeScheduleList(vaccinationNotificationId, oneTimeScheduleRequestList);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update one time schedules successfully")
                    .data(oneTimeScheduleList)
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
    public ResponseEntity<Object> deleteOneTimeSchedule(@PathVariable("id") Long oneTimeScheduleId) {
        try {
            oneTimeScheduleService.deleteOneTimeSchedule(oneTimeScheduleId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Delete one time schedule successfully")
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
