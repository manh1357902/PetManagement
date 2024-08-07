package com.project.petmanagement.payloads.requests;

import com.google.gson.annotations.SerializedName;
import com.project.petmanagement.models.enums.PaymentMethodEnum;

public class OrderRequest {
    @SerializedName("shipping_address")
    private String shippingAddress;
    private String phone;
    @SerializedName("payment_method")
    private PaymentMethodEnum paymentMethod;

    public OrderRequest(String shippingAddress, String phone, PaymentMethodEnum paymentMethod) {
        this.shippingAddress = shippingAddress;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}