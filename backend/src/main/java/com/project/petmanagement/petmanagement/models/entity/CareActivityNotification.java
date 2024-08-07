package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "care_activity_notifications")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareActivityNotification extends Notification {
    @OneToMany(mappedBy = "careActivityNotification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CareActivityInfo> careActivityInfoList;

    @OneToOne(mappedBy = "careActivityNotification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private RecurringSchedule schedule;

    @Column(name = "notification_status")
    private Boolean notificationStatus;
}
