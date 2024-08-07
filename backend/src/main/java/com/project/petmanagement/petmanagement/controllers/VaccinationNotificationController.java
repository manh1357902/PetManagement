package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.OneTimeSchedule;
import com.project.petmanagement.petmanagement.models.entity.VaccinationNotification;
import com.project.petmanagement.petmanagement.payloads.requests.VaccinationNotificationRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.OneTimeScheduleService;
import com.project.petmanagement.petmanagement.services.VaccinationNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/vaccination_notification")
@RequiredArgsConstructor
public class VaccinationNotificationController {
    private final OneTimeScheduleService oneTimeScheduleService;
    private final VaccinationNotificationService vaccinationNotificationService;

    @GetMapping("/users")
    public ResponseEntity<Object> getVaccinationNotificationByUser() {
        List<VaccinationNotification> vaccinationNotificationList = vaccinationNotificationService.getVaccinationNotificationByUser();
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get vaccination notification by user successfully")
                .data(vaccinationNotificationList)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<Object> getVaccinationNotificationByDate(@RequestParam Date date) {
        List<VaccinationNotification> vaccinationNotificationList = vaccinationNotificationService.getVaccinationNotificationByDate(date);
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get vaccination notification by date successfully")
                .data(vaccinationNotificationList)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVaccinationNotificationDetails(@PathVariable("id") Long vaccinationNotificationId) {
        try {
            VaccinationNotification vaccinationNotification = vaccinationNotificationService.getVaccinationNotificationDetails(vaccinationNotificationId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get vaccination notification details successfully")
                    .data(vaccinationNotification)
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
    public ResponseEntity<Object> addVaccinationNotification(@Valid @RequestBody VaccinationNotificationRequest vaccinationNotificationRequest) {
        try {
            VaccinationNotification vaccinationNotification = vaccinationNotificationService.addVaccinationNotification(vaccinationNotificationRequest);
            List<OneTimeSchedule> oneTimeScheduleList = oneTimeScheduleService.addOneTimeScheduleList(vaccinationNotificationRequest.getOneTimeScheduleRequestList(), vaccinationNotification);
            vaccinationNotification.setSchedules(oneTimeScheduleList);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add vaccination notification successfully")
                    .data(vaccinationNotification)
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
    public ResponseEntity<Object> updateVaccinationNotification(@PathVariable("id") Long vaccinationNotificationId, @Valid @RequestBody VaccinationNotificationRequest vaccinationNotificationRequest) {
        try {
            VaccinationNotification vaccinationNotification = vaccinationNotificationService.updateVaccinationNotification(vaccinationNotificationId, vaccinationNotificationRequest);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update vaccination notification successfully")
                    .data(vaccinationNotification)
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
    public ResponseEntity<Object> deleteVaccinationNotification(@PathVariable("id") Long vaccinationNotificationId) {
        try {
            vaccinationNotificationService.deleteVaccinationNotification(vaccinationNotificationId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Delete vaccination notification successfully")
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
