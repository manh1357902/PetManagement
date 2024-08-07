package com.project.petmanagement.petmanagement.repositories;

import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.models.entity.VaccinationNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationNotificationRepository extends JpaRepository<VaccinationNotification, Long> {
    List<VaccinationNotification> findByPet(Pet pet);
}
