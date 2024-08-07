package com.project.petmanagement.petmanagement.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OneTimeScheduleRequest {
    private Long id;

    @NotNull(message = "Date is required.")
    private Date date;

    @NotBlank(message = "Time is required.")
    private String time;

    private Boolean status;
}
