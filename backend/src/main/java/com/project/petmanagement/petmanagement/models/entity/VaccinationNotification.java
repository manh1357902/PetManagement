package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "vaccination_notifications")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationNotification extends Notification{
    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private Vaccine vaccine;

    @OneToMany(mappedBy = "vaccinationNotification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OneTimeSchedule> schedules;
}
