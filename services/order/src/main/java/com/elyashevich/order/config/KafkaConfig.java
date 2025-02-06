package com.elyashevich.order.config;

import com.elyashevich.order.domain.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaServers;

    @Value("${application.kafka.topic:order-topic")
    private String topicName;

    @Value("${application.kafka.partition:1}")
    private int partitionCount;

    @Value("${application.kafka.replica:1}")
    private int replicaCount;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder
                .name(this.topicName)
                .partitions(this.partitionCount)
                .replicas(this.replicaCount)
                .build();
    }

    @Bean
    public ProducerFactory<String, OrderEvent> producerFactory() {
        var config = new HashMap<String, Object>(3);
        config.put(BOOTSTRAP_SERVERS_CONFIG, this.kafkaServers);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, OrderEvent> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

    @Bean
    public ConsumerFactory<String, OrderEvent> consumerFactory() {
        var configProps = new HashMap<String, Object>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(GROUP_ID_CONFIG, "order-group");
        configProps.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(OrderEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEvent> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEvent>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}