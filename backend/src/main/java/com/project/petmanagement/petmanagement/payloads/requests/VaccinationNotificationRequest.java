package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationNotificationRequest {
    @JsonProperty("pet_id")
    @NotNull(message = "Pet can not be null.")
    private Long petId;

    private String title;

    @JsonProperty("vaccine_id")
    @NotNull(message = "Vaccine can not be null.")
    private Long vaccineId;

    private String note;

    @JsonProperty("schedules")
    @NotNull(message = "Schedule is required.")
    private List<OneTimeScheduleRequest> oneTimeScheduleRequestList;
}
