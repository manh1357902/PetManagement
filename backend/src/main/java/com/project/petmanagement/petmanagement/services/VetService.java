package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Vet;
import com.project.petmanagement.petmanagement.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetService {
    private final VetRepository vetRepository;

    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    public Vet getVetDetails(Long vetId) throws Exception {
        return vetRepository.findById(vetId).orElseThrow(() -> new DataNotFoundException("Can not find vet with ID: " + vetId));
    }

    public List<Vet> searchVet(String keywords) {
        return vetRepository.findByFullNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrWorkAtContainsIgnoreCaseOrClinicAddressContainsIgnoreCase(keywords, keywords, keywords, keywords, keywords);
    }
}
