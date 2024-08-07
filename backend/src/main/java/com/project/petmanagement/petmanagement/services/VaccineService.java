package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.models.entity.Vaccine;
import com.project.petmanagement.petmanagement.repositories.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccineService {
    private final VaccineRepository vaccineRepository;

    public List<Vaccine> getVaccinesBySpecies(Species species) {
        return vaccineRepository.findBySpecies(species);
    }
}
