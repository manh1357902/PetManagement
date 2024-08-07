package com.project.petmanagement.models.entity;

import java.sql.Time;

public class MonthlyRepetition extends Repetition {
    private Integer date;
    private Time time;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
