package com.project.petmanagement.petmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.petmanagement.petmanagement.models.entity.Species;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
}
