package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.DailyActivityLog;
import com.project.petmanagement.petmanagement.payloads.requests.DailyActivityLogRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.DailyActivityLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily_activity_logs")
public class DailyActivityLogController {
    private final DailyActivityLogService dailyActivityLogService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllDailyActivityLogs() {
        try {
            List<DailyActivityLog> dailyActivityLogList = dailyActivityLogService.getDailyActivityLogs();
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get all daily activity logs successfully")
                    .data(dailyActivityLogList)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDailyActivityLogById(@PathVariable("id") Long dailyActivityLogId) {
        try {
            DailyActivityLog dailyActivityLog = dailyActivityLogService.getDailyActivityLogById(dailyActivityLogId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get daily activity log by ID successfully")
                    .data(dailyActivityLog)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Object> getDailyActivityLogsByPet(@PathVariable("id") Long petId) {
        try {
            List<DailyActivityLog> dailyActivityLog = dailyActivityLogService.getDailyActivityLogsByPet(petId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get daily activity logs by pet successfully")
                    .data(dailyActivityLog)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addDailyActivityLog(@Valid @RequestBody DailyActivityLogRequest dailyActivityLogRequest) {
        try {
            DailyActivityLog dailyActivityLog = dailyActivityLogService.addDailyActivityLog(dailyActivityLogRequest);
            if (dailyActivityLog == null) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Cannot add daily activity log. Please try again!")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add daily activity log successfully")
                    .data(dailyActivityLog)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateDailyActivityLog(@RequestBody @Valid DailyActivityLogRequest dailyActivityLogRequest) {
        try {
            DailyActivityLog dailyActivityLog = dailyActivityLogService.updateDailyActivityLog(dailyActivityLogRequest);
            if (dailyActivityLog == null) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Cannot update daily activity log. Please try again")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update daily activity log successfully")
                    .data(dailyActivityLog)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDailyActivityLog(@PathVariable("id") Long dailyLogId) {
        try {
            dailyActivityLogService.deleteDailyActivityLog(dailyLogId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(200)
                    .message("Record deleted successfully")
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(400)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
