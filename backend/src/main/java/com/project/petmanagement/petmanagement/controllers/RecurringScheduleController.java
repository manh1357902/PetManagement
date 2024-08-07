package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.RecurringSchedule;
import com.project.petmanagement.petmanagement.payloads.requests.RecurringScheduleRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.RecurringScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recurring_schedules")
@RequiredArgsConstructor
public class RecurringScheduleController {
    private final RecurringScheduleService recurringScheduleService;

    @PutMapping("/update")
    public ResponseEntity<Object> updateRecurringSchedule(@Valid @RequestBody RecurringScheduleRequest recurringScheduleRequest) {
        try {
            RecurringSchedule recurringSchedule = recurringScheduleService.updateRecurringSchedule(recurringScheduleRequest);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update recurring schedule successfully")
                    .data(recurringSchedule)
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
