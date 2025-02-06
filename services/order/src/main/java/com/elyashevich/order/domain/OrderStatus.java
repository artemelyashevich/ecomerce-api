package com.elyashevich.order.domain;

public enum OrderStatus {

    PENDING("PENDING"),
    FULFILLED("FULFILLED"),
    REJECTED("REJECTED");

    OrderStatus(String status) {}
}
