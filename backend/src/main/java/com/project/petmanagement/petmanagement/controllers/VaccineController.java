package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.models.entity.Vaccine;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.PetService;
import com.project.petmanagement.petmanagement.services.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccines")
public class VaccineController {
    private final PetService petService;
    private final VaccineService vaccineService;

    @GetMapping("/pets/{id}")
    public ResponseEntity<Object> getVaccinesBySpeciesOfPet(@PathVariable("id") Long petId) {
        try {
            Pet pet = petService.getPet(petId);
            List<Vaccine> vaccineList = vaccineService.getVaccinesBySpecies(pet.getBreed().getSpecies());
            if (vaccineList.isEmpty()) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("There is no vaccine of species in database")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get vaccines of species successfully")
                    .data(vaccineList)
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
