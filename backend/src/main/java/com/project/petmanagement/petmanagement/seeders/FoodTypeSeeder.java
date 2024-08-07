package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.FoodType;
import com.project.petmanagement.petmanagement.repositories.FoodTypeRepository;

@Component
public class FoodTypeSeeder {
    @Autowired
    private FoodTypeRepository foodTypeRepository;

    public void seed() {
        FoodType foodType1 = FoodType.builder().id(1L).name("Thức ăn khô").build();
        FoodType foodType2 = FoodType.builder().id(2L).name("Thức ăn ướt").build();
        FoodType foodType3 = FoodType.builder().id(3L).name("Thức ăn đóng hộp").build();
        FoodType foodType4 = FoodType.builder().id(4L).name("Thức ăn tươi sống").build();
        FoodType foodType5 = FoodType.builder().id(5L).name("Thức ăn đóng gói").build();
        FoodType foodType6 = FoodType.builder().id(6L).name("Thức ăn chế biến sẵn").build();
        FoodType foodType7 = FoodType.builder().id(7L).name("Thức ăn đóng viên").build();
        FoodType foodType8 = FoodType.builder().id(8L).name("Thức ăn hạt").build();
        FoodType foodType9 = FoodType.builder().id(9L).name("Thức ăn hữu cơ").build();
        FoodType foodType10 = FoodType.builder().id(10L).name("Thức ăn thảo mộc").build();
        foodTypeRepository.saveAll(Arrays.asList(foodType1, foodType2, foodType3, foodType4, foodType5, foodType6,
                foodType7, foodType8, foodType9, foodType10));
    }
}
