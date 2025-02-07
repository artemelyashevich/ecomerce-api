package com.elyashevich.payment.dto;

public enum OrderStatus {

    PENDING("PENDING"),
    FULFILLED("FULFILLED"),
    REJECTED("REJECTED");

    OrderStatus(String status) {}
}
