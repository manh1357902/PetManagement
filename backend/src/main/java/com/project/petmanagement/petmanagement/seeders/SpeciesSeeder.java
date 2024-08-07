package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;

@Component
public class SpeciesSeeder {
    @Autowired
    private SpeciesRepository speciesRepository;

    public void seed() {
        Species dog = Species.builder().id(1L).name("Chó").build();
        Species cat = Species.builder().id(2L).name("Mèo").build();

        speciesRepository.saveAll(Arrays.asList(dog, cat));
    }
}
