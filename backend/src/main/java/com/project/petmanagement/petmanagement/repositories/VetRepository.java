package com.project.petmanagement.petmanagement.repositories;

import com.project.petmanagement.petmanagement.models.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    List<Vet> findByFullNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrWorkAtContainsIgnoreCaseOrClinicAddressContainsIgnoreCase(String fullName, String phoneNumber, String email, String workAt, String clinicAddress);
}
