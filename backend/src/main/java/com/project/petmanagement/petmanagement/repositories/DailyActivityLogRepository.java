package com.project.petmanagement.petmanagement.repositories;

import com.project.petmanagement.petmanagement.models.entity.DailyActivityLog;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyActivityLogRepository extends JpaRepository<DailyActivityLog, Long> {
    List<DailyActivityLog> findByPet(Pet pet);
}
