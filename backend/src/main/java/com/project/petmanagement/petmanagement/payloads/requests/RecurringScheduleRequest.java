package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.petmanagement.petmanagement.models.enums.FrequencyEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringScheduleRequest {
    private Long id;

    private String name;

    @NotNull(message = "Frequency is required.")
    private FrequencyEnum frequency;

    @NotNull(message = "Value of the repetition is required.")
    private Integer value;

    @JsonProperty("days_of_week")
    private List<DayOfWeek> daysOfWeek;

    private Date date;

    private String time;

    @JsonProperty("from_date")
    private Date fromDate;

    @JsonProperty("to_date")
    private Date toDate;
}
