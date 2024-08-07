package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "diseases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Prevention> preventions;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Treatment> treatments;

    @ManyToOne
    @JoinColumn(name = "species_id", referencedColumnName = "id")
    private Species species;
}
