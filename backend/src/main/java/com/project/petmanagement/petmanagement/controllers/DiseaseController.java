package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.Disease;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diseases")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService service;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllDiseases() {
        List<Disease> diseases = service.getAllDiseases();
        if (diseases.isEmpty()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("There is no disease food in database.")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all diseases successfully")
                .data(diseases)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDiseaseDetails(@PathVariable Long id) {
        try {
            Disease disease = service.getDiseaseDetails(id);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get disease details successfully")
                    .data(disease)
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
}
