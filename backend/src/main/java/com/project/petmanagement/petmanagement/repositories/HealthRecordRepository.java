package com.project.petmanagement.petmanagement.repositories;

import com.project.petmanagement.petmanagement.models.entity.HealthRecord;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByPet(Pet pet);
    HealthRecord findTopByOrderByIdDesc();

    List<HealthRecord> findByCheckUpDateBetweenAndPet(Date startDate, Date endDate, Pet pet);
}
