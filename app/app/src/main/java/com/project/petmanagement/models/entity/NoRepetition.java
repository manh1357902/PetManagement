package com.project.petmanagement.models.entity;

import java.sql.Time;
import java.util.Date;

public class NoRepetition {

    private Long id;
    private Date date;
    private Time time;
    private Time alarmBefore;
    private RecurringSchedule recurringSchedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
