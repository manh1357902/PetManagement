package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.petmanagement.petmanagement.models.enums.FrequencyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Table(name = "recurring_schedules")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecurringSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "frequency")
    @Enumerated(EnumType.STRING)
    private FrequencyEnum frequency;

    @Column(name = "value")
    private Integer value;

    @ElementCollection
    private List<DayOfWeek> daysOfWeek;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private String time;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @OneToOne
    @JoinColumn(name = "care_activity_notification_id", referencedColumnName = "id")
    @JsonBackReference
    private CareActivityNotification careActivityNotification;
}
