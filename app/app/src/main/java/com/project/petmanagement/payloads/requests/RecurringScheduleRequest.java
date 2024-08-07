package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;
import com.project.petmanagement.models.enums.FrequencyEnum;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

public class RecurringScheduleRequest implements Serializable {
    private Long id;

    private String name;

    private FrequencyEnum frequency;
    private Integer value;
    @SerializedName("days_of_week")
    private List<DayOfWeek> daysOfWeek;
    private String date;
    private String time;
    @SerializedName("from_date")
    private String fromDate;
    @SerializedName("to_date")
    private String toDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FrequencyEnum getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyEnum frequency) {
        this.frequency = frequency;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
