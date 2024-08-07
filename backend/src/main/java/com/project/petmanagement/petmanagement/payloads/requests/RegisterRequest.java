package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.petmanagement.petmanagement.constraints.annotations.Unique;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "Full name is required.")
    String fullName;

    @JsonProperty("date_of_birth")
    @NotNull(message = "Date of birth is required.")
    Date dateOfBirth;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required.")
    @Unique(fieldName = "phoneNumber", message = "Phone number has been registered.", table = "User")
    @Size(max = 10, min = 10, message = "Phone number should be included 10 digits.")
    String phoneNumber;

    @NotBlank(message = "Email is required.")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not in the right format.")
    String email;

    String address;

    String avatar;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must have at least 8 character.")
    String password;
}
