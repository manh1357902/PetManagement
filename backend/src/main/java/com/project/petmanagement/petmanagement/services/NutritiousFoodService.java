package com.project.petmanagement.petmanagement.services;

import java.util.List;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.FoodType;
import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.repositories.FoodTypeRepository;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;

import org.springframework.stereotype.Service;

import com.project.petmanagement.petmanagement.models.entity.NutritiousFood;
import com.project.petmanagement.petmanagement.repositories.NutritiousFoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NutritiousFoodService {
    private final NutritiousFoodRepository nutritiousFoodRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final SpeciesRepository speciesRepository;

    public List<NutritiousFood> getAllNutritiousFood() {
        return nutritiousFoodRepository.findAll();
    }

    public NutritiousFood getNutritiousFoodDetails(Long nutritiousFoodId) throws DataNotFoundException {
        return nutritiousFoodRepository.findById(nutritiousFoodId).orElseThrow(
                () -> new DataNotFoundException("Can not find nutritious food with ID: " + nutritiousFoodId));
    }

    public List<NutritiousFood> searchNutritiousFoodByFilter(String keyword, Long speciesId, Long foodTypeId)
            throws Exception {
        if (speciesId != null && foodTypeId != null) {
            Species existingSpecies = speciesRepository.findById(speciesId)
                    .orElseThrow(() -> new DataNotFoundException("Can not find species with ID: " + speciesId));
            FoodType existingFoodType = foodTypeRepository.findById(foodTypeId)
                    .orElseThrow(() -> new DataNotFoundException("Can not find food type with ID: " + foodTypeId));

            if (keyword != null && !keyword.isEmpty()) {
                return nutritiousFoodRepository.findByNameContainingAndFoodTypeAndSpecies(keyword, existingFoodType,
                        existingSpecies);
            } else {
                return nutritiousFoodRepository.findBySpeciesAndFoodType(existingSpecies, existingFoodType);
            }
        } else if (speciesId != null && keyword != null && !keyword.isEmpty()) {
            Species existingSpecies = speciesRepository.findById(speciesId)
                    .orElseThrow(() -> new DataNotFoundException("Can not find species with ID: " + speciesId));
            return nutritiousFoodRepository.findByNameContainingAndSpecies(keyword, existingSpecies);
        } else if (foodTypeId != null && keyword != null && !keyword.isEmpty()) {
            FoodType existingFoodType = foodTypeRepository.findById(foodTypeId)
                    .orElseThrow(() -> new DataNotFoundException("Can not find food type with ID: " + foodTypeId));

            return nutritiousFoodRepository.findByNameContainingAndFoodType(keyword, existingFoodType);
        } else if (speciesId != null) {
            Species existingSpecies = speciesRepository.findById(speciesId)
                    .orElseThrow(() -> new DataNotFoundException("Can not find species with ID: " + speciesId));
            return nutritiousFoodRepository.findBySpecies(existingSpecies);
        } else if (foodTypeId != null) {
            FoodType existingFoodType = foodTypeRepository.findById(foodTypeId)
                    .orElseThrow(() -> new DataNotFoundException("Can not find food type with ID: " + foodTypeId));

            return nutritiousFoodRepository.findByFoodType(existingFoodType);
        } else {
            if (keyword != null && !keyword.isEmpty()) {
                return nutritiousFoodRepository.findByNameContaining(keyword);
            } else {
                throw new IllegalArgumentException("At least one parameter must be provided.");
            }
        }
    }
}
