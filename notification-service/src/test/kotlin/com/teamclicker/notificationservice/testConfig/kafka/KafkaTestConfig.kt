package com.teamclicker.notificationservice.testConfig.kafka

import com.teamclicker.notificationservice.kafka.ALL_KAFKA_TOPICS
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.kafka.test.utils.KafkaTestUtils

@Configuration
class KafkaTestConfig {
    fun producerFactory(kafkaEmbedded: KafkaEmbedded): ProducerFactory<String, Any> {
        return DefaultKafkaProducerFactory(
            KafkaTestUtils.producerProps(kafkaEmbedded).also {
                it.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
                it.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
            })
    }

    @Bean
    fun kafkaTemplate(kafkaEmbedded: KafkaEmbedded): KafkaTemplate<String, Any> {
        val kafkaTemplate = KafkaTemplate(producerFactory(kafkaEmbedded))
        kafkaTemplate.defaultTopic = "defaultTopic"
        return kafkaTemplate
    }

    @Bean
    fun kafkaEmbedded(): KafkaEmbedded {
        return KafkaEmbedded(1, true, 1, *ALL_KAFKA_TOPICS)
            .brokerProperties(
                mapOf(
                    "listeners" to "PLAINTEXT://localhost:3333",
                    "port" to "333"
                )
            )
    }
}