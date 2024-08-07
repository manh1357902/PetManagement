package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.MedicalDocument;
import com.project.petmanagement.petmanagement.payloads.requests.MedicalDocumentRequest;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.FirebaseService;
import com.project.petmanagement.petmanagement.services.MedicalDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medical_documents")
@RequiredArgsConstructor
public class MedicalDocumentController {
    private final FirebaseService firebaseService;
    private final MedicalDocumentService medicalDocumentService;

    @GetMapping(value = "/pets/{id}")
    public ResponseEntity<Object> getMedicalDocumentByPet(@PathVariable("id") Long petId) {
        try {
            List<MedicalDocument> medicalDocumentList = medicalDocumentService.getMedicalDocumentByPet(petId);
            DataResponse response = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get medical documents by pet successfully")
                    .data(medicalDocumentList)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedicalDocumentDetails(@PathVariable("id") Long medicalDocumentId) {
        try {
            MedicalDocument medicalDocument = medicalDocumentService.getMedicalDocumentDetails(medicalDocumentId);
            String url = firebaseService.getDownloadUrl(medicalDocument.getFileName());
            medicalDocument.setUrl(url);
            DataResponse response = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get medical document details successfully")
                    .data(medicalDocument)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addMedicalDocument(@Valid @ModelAttribute MedicalDocumentRequest medicalDocumentRequest) {
        try {
            MultipartFile file = medicalDocumentRequest.getFile();
            if (file.getSize() == 0) {
                ErrorResponse response = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("File is empty")
                        .build();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (file.getSize() > 10 * 1024 * 1024) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("File is too large. Maximum size is 10MB.")
                        .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            byte[] fileBytes = file.getBytes();
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            firebaseService.uploadFile(uniqueFileName, fileBytes);
            MedicalDocument medicalDocument = medicalDocumentService.addMedicalDocument(medicalDocumentRequest, uniqueFileName);
            DataResponse response = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add medical document successfully")
                    .data(medicalDocument)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataNotFoundException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMedicalDocument(@PathVariable("id") Long medicalDocumentId, @Valid @ModelAttribute MedicalDocumentRequest medicalDocumentRequest) {
        try {
            MultipartFile file = medicalDocumentRequest.getFile();
            if (file.getSize() == 0) {
                ErrorResponse response = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("File is empty")
                        .build();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Boolean deleted = firebaseService.deleteFile(medicalDocumentService.getMedicalDocumentDetails(medicalDocumentId).getFileName());
            if (deleted) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    ErrorResponse errorResponse = ErrorResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("File is too large. Maximum size is 10MB.")
                            .build();
                    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
                }
                byte[] fileBytes = file.getBytes();
                String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                firebaseService.uploadFile(uniqueFileName, fileBytes);
                MedicalDocument medicalDocument = medicalDocumentService.updateMedicalDocument(medicalDocumentId, medicalDocumentRequest, uniqueFileName);
                DataResponse response = DataResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Add medical document successfully")
                        .data(medicalDocument)
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Can not update file in Firebase")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataNotFoundException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMedicalDocument(@PathVariable("id") Long medicalDocumentId) {
        try {
            Boolean deleted = firebaseService.deleteFile(medicalDocumentService.getMedicalDocumentDetails(medicalDocumentId).getFileName());
            medicalDocumentService.deleteMedicalDocument(medicalDocumentId);
            if (deleted) {
                DataResponse dataResponse = DataResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Delete medical document successfully")
                        .build();
                return new ResponseEntity<>(dataResponse, HttpStatus.OK);
            }
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Can not delete file in Firebase")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataNotFoundException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
