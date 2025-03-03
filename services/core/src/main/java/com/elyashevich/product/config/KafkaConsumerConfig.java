package com.elyashevich.product.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;

import static com.elyashevich.product.util.KafkaConstantUtil.GROUP_ID;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
@EnableKafka
@ConditionalOnProperty(value = "kafka.enabled")
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}:localhost:9092")
    private String kafkaServers;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        var configProps = new HashMap<String, Object>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaServers);
        configProps.put(GROUP_ID_CONFIG, GROUP_ID);
        configProps.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}