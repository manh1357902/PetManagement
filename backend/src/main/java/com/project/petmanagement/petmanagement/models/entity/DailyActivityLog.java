package com.project.petmanagement.petmanagement.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "daily_activity_logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private String time;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "daily_activity_id", referencedColumnName = "id")
    private DailyActivity dailyActivity;
}
