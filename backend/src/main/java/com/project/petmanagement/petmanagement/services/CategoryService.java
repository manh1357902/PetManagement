package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.models.entity.Category;
import com.project.petmanagement.petmanagement.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}