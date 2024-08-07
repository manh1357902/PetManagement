package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.HealthRecord;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.payloads.requests.HealthRecordRequest;
import com.project.petmanagement.petmanagement.repositories.HealthRecordRepository;
import com.project.petmanagement.petmanagement.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordService {
    private final HealthRecordRepository healthRecordRepository;
    private final PetRepository petRepository;

    public List<HealthRecord> getHealthRecordByPet(Long petId) throws Exception {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new DataNotFoundException("Can not found Pet with id=" + petId));
        return healthRecordRepository.findByPet(pet);
    }

    @Transactional(rollbackFor = Exception.class)
    public HealthRecord addHealRecord(HealthRecordRequest healthRecordRequest) throws Exception {
        HealthRecord lastHealthRecord = healthRecordRepository.findTopByOrderByIdDesc();
        Pet pet = petRepository.findById(healthRecordRequest.getPetId()).orElseThrow(
                () -> new DataNotFoundException("Pet not found with id=" + healthRecordRequest.getPetId()));
        HealthRecord healthRecord = HealthRecord.builder()
                .checkUpDate(healthRecordRequest.getCheckUpDate())
                .weight(healthRecordRequest.getWeight())
                .exerciseLevel(healthRecordRequest.getExerciseLevel())
                .symptoms(healthRecordRequest.getSymptoms())
                .note(healthRecordRequest.getNote())
                .diagnosis(healthRecordRequest.getDiagnosis())
                .pet(pet)
                .build();
        if (lastHealthRecord != null) {
            healthRecord.setLastVisit(lastHealthRecord.getCheckUpDate());
        } else {
            healthRecord.setLastVisit(null);
        }
        return healthRecordRepository.save(healthRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public HealthRecord updateHealthRecord(HealthRecordRequest healthRecordRequest, Long id) throws Exception {
        HealthRecord healthRecord = healthRecordRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not found health record with id=" + id));
        healthRecord.setCheckUpDate(healthRecordRequest.getCheckUpDate());
        healthRecord.setWeight(healthRecordRequest.getWeight());
        healthRecord.setExerciseLevel(healthRecordRequest.getExerciseLevel());
        healthRecord.setSymptoms(healthRecordRequest.getSymptoms());
        healthRecord.setNote(healthRecordRequest.getNote());
        healthRecord.setDiagnosis(healthRecordRequest.getDiagnosis());
        return healthRecordRepository.save(healthRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteHealthRecord(Long id) throws Exception {
        HealthRecord healthRecord = healthRecordRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not found HealthRecord with id=" + id));
        try {
            healthRecordRepository.delete(healthRecord);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<HealthRecord> getListHealthRecordByFilter(Long petId, Date startDate, Date endDate) {
        return healthRecordRepository.findByCheckUpDateBetweenAndPet(startDate, endDate,
                petRepository.findById(petId).get());
    }
}
