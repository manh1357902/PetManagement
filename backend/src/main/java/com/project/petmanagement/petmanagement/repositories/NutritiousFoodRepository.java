package com.project.petmanagement.petmanagement.repositories;

import com.project.petmanagement.petmanagement.models.entity.FoodType;
import com.project.petmanagement.petmanagement.models.entity.NutritiousFood;
import com.project.petmanagement.petmanagement.models.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritiousFoodRepository extends JpaRepository<NutritiousFood, Long> {
    List<NutritiousFood> findBySpecies(Species species);

    List<NutritiousFood> findByFoodType(FoodType foodType);

    List<NutritiousFood> findBySpeciesAndFoodType(Species species, FoodType foodType);

    List<NutritiousFood> findByNameContaining(String name);

    List<NutritiousFood> findByNameContainingAndSpecies(String name, Species species);

    List<NutritiousFood> findByNameContainingAndFoodType(String name, FoodType foodType);

    List<NutritiousFood> findByNameContainingAndFoodTypeAndSpecies(String name, FoodType foodType, Species species);
}
