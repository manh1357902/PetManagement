package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    public List<Species> getAllSpecies() {
        return speciesRepository.findAll();
    }

    public Species getSpeciesDetails(Long speciesId) throws Exception {
        return speciesRepository.findById(speciesId).orElseThrow(() -> new DataNotFoundException("Can not find species with ID: " + speciesId));
    }
}
