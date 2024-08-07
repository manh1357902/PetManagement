package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.Breed;
import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.repositories.BreedRepository;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;

@Component
public class BreedSeeder {
    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    public void seed() {
        List<Species> listSpecies = speciesRepository.findAll();

        Breed b1 = Breed.builder().id(1L).name("Labrador Retriever").species(listSpecies.get(0)).build();
        Breed b2 = Breed.builder().id(2L).name("German Shepherd").species(listSpecies.get(0)).build();
        Breed b3 = Breed.builder().id(3L).name("Golden Retriever").species(listSpecies.get(0)).build();
        Breed b4 = Breed.builder().id(4L).name("Bulldog").species(listSpecies.get(0)).build();
        Breed b5 = Breed.builder().id(5L).name("Beagle").species(listSpecies.get(0)).build();
        Breed b6 = Breed.builder().id(6L).name("Poodle").species(listSpecies.get(0)).build();
        Breed b7 = Breed.builder().id(7L).name("Siberian Husky").species(listSpecies.get(0)).build();
        Breed b8 = Breed.builder().id(8L).name("Dachshund").species(listSpecies.get(0)).build();
        Breed b9 = Breed.builder().id(9L).name("Boxer").species(listSpecies.get(0)).build();
        Breed b10 = Breed.builder().id(10L).name("Shih Tzu").species(listSpecies.get(0)).build();
        Breed b11 = Breed.builder().id(11L).name("Persian").species(listSpecies.get(1)).build();
        Breed b12 = Breed.builder().id(12L).name("Siamese").species(listSpecies.get(1)).build();
        Breed b13 = Breed.builder().id(13L).name("Maine Coon").species(listSpecies.get(1)).build();
        Breed b14 = Breed.builder().id(14L).name("Ragdoll").species(listSpecies.get(1)).build();
        Breed b15 = Breed.builder().id(15L).name("Bengal").species(listSpecies.get(1)).build();
        Breed b16 = Breed.builder().id(16L).name("Sphynx").species(listSpecies.get(1)).build();
        Breed b17 = Breed.builder().id(17L).name("Abyssinian").species(listSpecies.get(1)).build();
        Breed b18 = Breed.builder().id(18L).name("Scottish Fold").species(listSpecies.get(1)).build();
        Breed b19 = Breed.builder().id(19L).name("Norwegian Forest Cat").species(listSpecies.get(1)).build();
        Breed b20 = Breed.builder().id(20L).name("Burmese").species(listSpecies.get(1)).build();

        breedRepository.saveAll(
                Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20));
    }
}
