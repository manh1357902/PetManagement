package com.project.petmanagement.models.entity;

import java.util.Date;

public class HealthStatistics extends HealthRecord {
    private Date fromDate;
    private Date toDate;
    private Double totalChangedWeight;

    public HealthStatistics(Date checkUpDate, Double weight) {
        super(checkUpDate, weight);
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Double getTotalChangedWeight() {
        return totalChangedWeight;
    }

    public void setTotalChangedWeight(Double totalChangedWeight) {
        this.totalChangedWeight = totalChangedWeight;
    }
}
