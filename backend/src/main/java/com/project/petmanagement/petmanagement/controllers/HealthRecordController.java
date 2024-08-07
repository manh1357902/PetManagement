package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.HealthRecord;
import com.project.petmanagement.petmanagement.payloads.requests.HealthRecordRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.HealthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health_records")
public class HealthRecordController {
    private final HealthRecordService healthRecordService;

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getHealthRecordByPet(@PathVariable(name = "id") Long petId) {
        try {
            List<HealthRecord> healthRecords = healthRecordService.getHealthRecordByPet(petId);
            DataResponse healthRecordResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get health records success.")
                    .data(healthRecords)
                    .build();
            return new ResponseEntity<>(healthRecordResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addHeathRecord(@Valid @RequestBody HealthRecordRequest healthRecordRequest) {
        try {
            HealthRecord healthRecord = healthRecordService.addHealRecord(healthRecordRequest);
            DataResponse healthRecordResponse = DataResponse.builder().status(HttpStatus.CREATED.value())
                    .message("Add health record success.").data(healthRecord).build();
            return new ResponseEntity<>(healthRecordResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHealthRecord(@PathVariable("id") Long idHealRecord,
                                                @Valid @RequestBody HealthRecordRequest healthRecordRequest) {
        try {
            HealthRecord healthRecord = healthRecordService.updateHealthRecord(healthRecordRequest, idHealRecord);
            DataResponse healthRecordResponse = DataResponse.builder().status(HttpStatus.CREATED.value())
                    .message("Update health record success.").data(healthRecord).build();
            return new ResponseEntity<>(healthRecordResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHealthRecord(@PathVariable("id") Long id) {
        try {
            if (healthRecordService.deleteHealthRecord(id)) {
                DataResponse dataResponse = DataResponse.builder()
                        .message("Delete health record success.")
                        .status(HttpStatus.OK.value())
                        .build();
                return new ResponseEntity<>(dataResponse, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Cannot delete health record. Please try again!")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/static")
    public ResponseEntity<Object> getListHealthRecordsByFilter(@RequestParam(value = "pet_id") Long petId,
                                                               @RequestParam(value = "start_date") Date starDate,
                                                               @RequestParam(value = "end_date") Date endDate) {
        try {
            DataResponse response = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get health records successfully")
                    .data(healthRecordService.getListHealthRecordByFilter(petId, starDate, endDate))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
