package com.elyashevich.product.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaConstantUtil {

    public static final String DESTINATION = "/topic/notifications";
    public static final String GROUP_ID = "notification-group";
}