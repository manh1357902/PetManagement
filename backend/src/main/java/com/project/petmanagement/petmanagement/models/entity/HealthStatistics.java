package com.project.petmanagement.petmanagement.models.entity;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthStatistics extends HealthRecord {
    private Date fromDate;

    private Date toDate;

    private Double totalChangedWeight;
}
