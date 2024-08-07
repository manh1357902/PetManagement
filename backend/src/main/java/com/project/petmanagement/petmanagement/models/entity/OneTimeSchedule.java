package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "one_time_schedules")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OneTimeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vaccination_notification_id", referencedColumnName = "id")
    @JsonBackReference
    private VaccinationNotification vaccinationNotification;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private String time;

    @Column(name = "vaccination_status")
    private Boolean vaccinationStatus;
}
