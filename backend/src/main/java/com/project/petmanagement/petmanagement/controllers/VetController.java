package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.Vet;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    private final VetService vetService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllVets() {
        List<Vet> vets = vetService.getAllVets();
        if (!vets.isEmpty()) {
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get all vets successfully")
                    .data(vets)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("There is no vets in database")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVetDetails(@PathVariable("id") Long vetId) {
        try {
            Vet vet = vetService.getVetDetails(vetId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get vet details successfully")
                    .data(vet)
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

    @GetMapping("/search")
    public ResponseEntity<Object> findVets(@RequestParam(value = "keywords") String keywords) {
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Find vets with keywords " + keywords + " successfully")
                .data(vetService.searchVet(keywords))
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
