package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "Full name is required.")
    private String fullName;

    @JsonProperty("date_of_birth")
    @NotNull(message = "Date of birth is required.")
    private Date dateOfBirth;

    @NotBlank(message = "Email is required.")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not in the right format.")
    private String email;

    private String address;

    private String avatar;
}
