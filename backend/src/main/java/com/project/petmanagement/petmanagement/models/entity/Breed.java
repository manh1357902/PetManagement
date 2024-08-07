package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "breeds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "species_id", referencedColumnName = "id")
    @JsonBackReference
    private Species species;
}
