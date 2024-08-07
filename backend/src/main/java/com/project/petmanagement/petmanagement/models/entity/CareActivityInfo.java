package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "care_activity_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareActivityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "care_activity_id", referencedColumnName = "id")
    private CareActivity careActivity;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "care_activity_notification_id", referencedColumnName = "id")
    @JsonBackReference
    private CareActivityNotification careActivityNotification;
}

