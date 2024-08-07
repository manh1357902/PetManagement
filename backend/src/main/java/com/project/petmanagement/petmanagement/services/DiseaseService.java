package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Disease;
import com.project.petmanagement.petmanagement.repositories.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {
    private final DiseaseRepository repository;

    public List<Disease> getAllDiseases() {
        return repository.findAll();
    }

    public Disease getDiseaseDetails(Long id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("Can not find disease with ID: " + id));
    }
}
