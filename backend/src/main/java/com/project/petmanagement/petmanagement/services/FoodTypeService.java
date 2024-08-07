package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.models.entity.FoodType;
import com.project.petmanagement.petmanagement.repositories.FoodTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;

    public List<FoodType> getAllFoodTypes() {
        return foodTypeRepository.findAll();
    }
}
