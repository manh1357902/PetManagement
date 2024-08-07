package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.petmanagement.petmanagement.models.enums.ProductStatusEnum;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "products")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatusEnum status;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;
}
