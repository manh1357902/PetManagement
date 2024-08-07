package com.project.petmanagement.petmanagement.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "vets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_numer")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "experience")
    private Double experience;

    @Column(name = "work_at")
    private String workAt;

    @Column(name = "clinic_address")
    private String clinicAddress;
}
