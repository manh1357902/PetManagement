package com.project.petmanagement.petmanagement.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.petmanagement.petmanagement.models.enums.PaymentMethodEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message = "Shipping address is required.")
    @JsonProperty("shipping_address")
    private String shippingAddress;

    @NotBlank(message = "Phone is required.")
    private String phone;

    @NotNull(message = "Payment method is required")
    @JsonProperty("payment_method")
    private PaymentMethodEnum paymentMethod;
}
