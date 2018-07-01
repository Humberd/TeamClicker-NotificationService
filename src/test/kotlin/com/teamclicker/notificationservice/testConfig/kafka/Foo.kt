package com.teamclicker.notificationservice.testConfig.kafka

import org.springframework.context.annotation.Bean
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.stereotype.Service

@Service
class Foo {

    @Bean
    fun aaa(): KafkaEmbedded {
        return KafkaEmbedded(1, true, 1)
            .brokerProperties(
                mapOf(
                    "listeners" to "PLAINTEXT://localhost:3333",
                    "port" to "333"
                )
            )
    }
}