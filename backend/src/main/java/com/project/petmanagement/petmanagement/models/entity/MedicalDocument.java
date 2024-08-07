package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medical_documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @JsonIgnore
    @Column(name = "file_name")
    private String fileName;

    @JsonProperty("url")
    @Transient
    private String url;
}
