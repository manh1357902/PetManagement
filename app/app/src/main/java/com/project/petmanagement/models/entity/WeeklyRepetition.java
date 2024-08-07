package com.project.petmanagement.models.entity;

import java.sql.Time;
import java.time.DayOfWeek;

public class WeeklyRepetition {
    private DayOfWeek day;
    private Time time;

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
