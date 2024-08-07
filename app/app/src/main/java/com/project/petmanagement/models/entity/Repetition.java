package com.project.petmanagement.models.entity;

import java.sql.Time;
import java.util.Date;

public class Repetition {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Time alarmBefore;
    private RecurringSchedule recurringSchedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Time getAlarmBefore() {
        return alarmBefore;
    }

    public void setAlarmBefore(Time alarmBefore) {
        this.alarmBefore = alarmBefore;
    }

    public RecurringSchedule getRecurringSchedule() {
        return recurringSchedule;
    }

    public void setRecurringSchedule(RecurringSchedule recurringSchedule) {
        this.recurringSchedule = recurringSchedule;
    }
}
