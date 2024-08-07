package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.petmanagement.petmanagement.constraints.annotations.Changed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    
    @NotBlank(message = "Please enter old password.")
    @Changed(message = "Old password is not matched.", condition = true)
    @JsonProperty("old_password")
    String oldPassword;

    @NotBlank(message = "Please enter new password.")
    @Changed(message = "New password must be different to old password.", condition = false)
    @JsonProperty("new_password")
    String newPassword;

    @NotBlank(message = "Please reenter new password.")
    @JsonProperty("renew_password")
    String renewPassword;

}
