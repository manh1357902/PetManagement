package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.payloads.requests.PetRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/users")
    public ResponseEntity<Object> getPetsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDetail userDetail = (JWTUserDetail) authentication.getPrincipal();
        List<Pet> pets = petService.getPetsByUser(userDetail.getUser());
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get pets by user successfully")
                .data(pets)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPetDetails(@PathVariable("id") Long petId) {
        try {
            Pet pet = petService.getPet(petId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get pet details successfully")
                    .data(pet)
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
    public ResponseEntity<Object> addPet(@RequestBody @Valid PetRequest petRequest) {
        try {
            Pet pet = petService.addPet(petRequest);
            if (pet == null) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Cannot add pet. Please try again!")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add pet successfully")
                    .data(pet)
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

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePet(@RequestBody @Valid PetRequest petRequest, @PathVariable Long id) {
        try {
            Pet pet = petService.updatePet(id, petRequest);
            if (pet == null) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Cannot update pet. Please try again")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update pet successfully")
                    .data(pet)
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
    public ResponseEntity<Object> deletePet(@PathVariable("id") Long petId) {
        try {
            Pet pet = petService.deletePet(petId);
            if (pet == null) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Cannot delete pet. Please try again")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Delete pet successfully")
                    .data(pet)
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
