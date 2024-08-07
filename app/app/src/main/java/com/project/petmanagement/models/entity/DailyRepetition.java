package com.project.petmanagement.models.entity;

import java.sql.Time;

public class DailyRepetition extends Repetition {
    private Time time;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
