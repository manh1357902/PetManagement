package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.models.entity.Vaccine;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;
import com.project.petmanagement.petmanagement.repositories.VaccineRepository;

@Component
public class VaccineSeeder {
    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    public void seed() {
        List<Species> listSpecies = speciesRepository.findAll();

        Vaccine v1 = Vaccine.builder().id(1L).name("Vaccine Parvovirus").vaccineDose(1).species(listSpecies.get(0)).build();
        Vaccine v2 = Vaccine.builder().id(2L).name("Vaccine Distemper").vaccineDose(1).species(listSpecies.get(0)).build();
        Vaccine v3 = Vaccine.builder().id(3L).name("Vaccine Hepatitis C").vaccineDose(1).species(listSpecies.get(0)).build();
        Vaccine v4 = Vaccine.builder().id(4L).name("Vaccine Parainfluenza").vaccineDose(1).species(listSpecies.get(0)).build();
        Vaccine v5 = Vaccine.builder().id(5L).name("Vaccine Rabies").vaccineDose(1).species(listSpecies.get(0)).build();
        Vaccine v6 = Vaccine.builder().id(6L).name("Vaccine Panleukopenia").vaccineDose(1).species(listSpecies.get(1)).build();
        Vaccine v7 = Vaccine.builder().id(7L).name("Vaccine Calicivirus").vaccineDose(1).species(listSpecies.get(1)).build();
        Vaccine v8 = Vaccine.builder().id(8L).name("Vaccine Herpesvirus").vaccineDose(1).species(listSpecies.get(1)).build();
        Vaccine v9 = Vaccine.builder().id(9L).name("Vaccine Chlamydia").vaccineDose(1).species(listSpecies.get(1)).build();
        Vaccine v10 = Vaccine.builder().id(10L).name("Vaccine Rabies").vaccineDose(1).species(listSpecies.get(1)).build();

        vaccineRepository.saveAll(Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10));
    }
}
