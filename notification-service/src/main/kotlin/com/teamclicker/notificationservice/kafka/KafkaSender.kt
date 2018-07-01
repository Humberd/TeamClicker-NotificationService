package com.teamclicker.notificationservice.kafka

import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaSender(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    internal fun send(topic: KafkaTopic, data: Any) {
        kafkaTemplate.send(topic.value, data)
    }

    fun send(passwordResetEmailKDTO: PasswordResetEmailKDTO) {
        send(KafkaTopic.PASSWORD_RESET_EMAIL, passwordResetEmailKDTO)
    }

}