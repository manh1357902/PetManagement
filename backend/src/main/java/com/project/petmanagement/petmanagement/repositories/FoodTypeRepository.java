package com.project.petmanagement.petmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.petmanagement.petmanagement.models.entity.FoodType;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
}
