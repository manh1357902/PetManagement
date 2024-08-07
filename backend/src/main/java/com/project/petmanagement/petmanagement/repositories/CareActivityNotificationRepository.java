package com.project.petmanagement.petmanagement.repositories;

import com.project.petmanagement.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareActivityNotificationRepository extends JpaRepository<CareActivityNotification, Long> {
    List<CareActivityNotification> findByPet(Pet pet);
}
