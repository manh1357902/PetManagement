package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyActivityLogRequest {
    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    @NotNull(message = "Date is required.")
    private Date date;

    @NotBlank(message = "Time is required.")
    private String time;

    private String image;

    private String note;

    @JsonProperty("pet_id")
    @NotNull(message = "Pet can not be null.")
    private Long petId;

    @JsonProperty("daily_activity_id")
    @NotNull(message = "Daily activity can not be null.")
    private Long dailyActivityId;
}
