package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.MedicalDocument;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.payloads.requests.MedicalDocumentRequest;
import com.project.petmanagement.petmanagement.repositories.MedicalDocumentRepository;
import com.project.petmanagement.petmanagement.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalDocumentService {
    private final PetRepository petRepository;
    private final MedicalDocumentRepository medicalDocumentRepository;

    public List<MedicalDocument> getMedicalDocumentByPet(Long petId) throws DataNotFoundException {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + petId));
        return medicalDocumentRepository.findByPet(pet);
    }

    public MedicalDocument getMedicalDocumentDetails(Long medicalDocumentId) throws DataNotFoundException {
        return medicalDocumentRepository.findById(medicalDocumentId).orElseThrow(() -> new DataNotFoundException("Can not find medical document with ID: " + medicalDocumentId));
    }

    @Transactional(rollbackFor = {Exception.class})
    public MedicalDocument addMedicalDocument(MedicalDocumentRequest medicalDocumentRequest, String uniqueFileName) throws DataNotFoundException {
        MedicalDocument medicalDocument = MedicalDocument.builder()
                .title(medicalDocumentRequest.getTitle())
                .note(medicalDocumentRequest.getNote())
                .pet(petRepository.findById(medicalDocumentRequest.getPetId()).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + medicalDocumentRequest.getPetId())))
                .fileName(uniqueFileName)
                .build();
        return medicalDocumentRepository.save(medicalDocument);
    }

    @Transactional(rollbackFor = {Exception.class})
    public MedicalDocument updateMedicalDocument(Long medicalDocumentId, MedicalDocumentRequest medicalDocumentRequest, String uniqueFileName) throws DataNotFoundException {
        MedicalDocument existingMedicalDocument = medicalDocumentRepository.findById(medicalDocumentId).orElseThrow(() -> new DataNotFoundException("Can not find medical document with ID: " + medicalDocumentId));
        existingMedicalDocument.setTitle(medicalDocumentRequest.getTitle());
        existingMedicalDocument.setNote(medicalDocumentRequest.getNote());
        existingMedicalDocument.setFileName(uniqueFileName);
        return medicalDocumentRepository.save(existingMedicalDocument);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteMedicalDocument(Long medicalDocumentId) throws DataNotFoundException {
        MedicalDocument existingMedicalDocument = medicalDocumentRepository.findById(medicalDocumentId).orElseThrow(() -> new DataNotFoundException("Can not find medical document with ID: " + medicalDocumentId));
        if (existingMedicalDocument != null) {
            medicalDocumentRepository.deleteById(medicalDocumentId);
        }
    }
}
