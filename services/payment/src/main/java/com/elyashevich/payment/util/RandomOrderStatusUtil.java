package com.elyashevich.payment.util;

import com.elyashevich.payment.dto.OrderStatus;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomOrderStatusUtil {

    public OrderStatus getRandomOrderStatus() {
        var random = new Random().nextInt(OrderStatus.values().length);
        return OrderStatus.values()[random];
    }
}
