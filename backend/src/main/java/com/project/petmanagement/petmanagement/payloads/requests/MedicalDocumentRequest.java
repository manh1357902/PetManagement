package com.project.petmanagement.petmanagement.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalDocumentRequest {
    @NotBlank(message = "Title is required.")
    private String title;

    private String note;

    @NotNull(message = "Pet can not be null")
    private Long petId;

    @NotNull(message = "Document file is required.")
    private MultipartFile file;
}
