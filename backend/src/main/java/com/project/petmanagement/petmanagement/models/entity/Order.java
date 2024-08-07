package com.project.petmanagement.petmanagement.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.petmanagement.petmanagement.models.enums.OrderStatusEnum;
import com.project.petmanagement.petmanagement.models.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "order_date")
    private Date orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    private Double totalPrice;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
}
