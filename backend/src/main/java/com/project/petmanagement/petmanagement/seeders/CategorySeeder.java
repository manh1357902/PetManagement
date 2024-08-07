package com.project.petmanagement.petmanagement.seeders;

import com.project.petmanagement.petmanagement.models.entity.Category;
import com.project.petmanagement.petmanagement.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CategorySeeder {
    @Autowired
    private CategoryRepository categoryRepository;

    public void seed(){
        Category category1 = Category.builder().id(1L).name("Thức ăn").build();
        Category category2 = Category.builder().id(2L).name("Thiết bị").build();
        Category category3 = Category.builder().id(3L).name("Đồ chơi").build();

        categoryRepository.saveAll(Arrays.asList(category1,category2, category3));
    }
}