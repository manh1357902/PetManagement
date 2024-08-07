package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/species")
@RequiredArgsConstructor
public class SpeciesController {
    private final SpeciesService speciesService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllSpecies() {
        List<Species> speciesList = speciesService.getAllSpecies();
        if (!speciesList.isEmpty()) {
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get all species successfully")
                    .data(speciesList)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("There is no species in Database")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSpeciesDetails(@PathVariable("id") Long speciesId) {
        try {
            Species species = speciesService.getSpeciesDetails(speciesId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get species details successfully")
                    .data(species)
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
