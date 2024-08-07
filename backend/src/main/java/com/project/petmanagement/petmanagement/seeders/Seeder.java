package com.project.petmanagement.petmanagement.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final RoleSeeder roleSeeder;
    private final FoodTypeSeeder foodTypeSeeder;
    private final NutritiousFoodSeeder nutritiousFoodSeeder;
    private final SpeciesSeeder speciesSeeder;
    private final BreedSeeder breedSeeder;
    private final CategorySeeder categorySeeder;
    private final ProductSeeder productSeeder;
    private final VetSeeder vetSeeder;
    private final DiseaseSeeder diseaseSeeder;
    private final PreventionSeeder preventionSeeder;
    private final TreatmentSeeder treatmentSeeder;
    private final DailyActivitySeeder dailyActivitySeeder;
    private final VaccineSeeder vaccineSeeder;

    @Override
    public void run(String... args) throws Exception {
        speciesSeeder.seed();
        roleSeeder.seed();
        foodTypeSeeder.seed();
        nutritiousFoodSeeder.seed();
        breedSeeder.seed();
        categorySeeder.seed();
        productSeeder.seed();
        vetSeeder.seed();
        diseaseSeeder.seed();
        preventionSeeder.seed();
        treatmentSeeder.seed();
        dailyActivitySeeder.seed();
        vaccineSeeder.seed();
    }
}
