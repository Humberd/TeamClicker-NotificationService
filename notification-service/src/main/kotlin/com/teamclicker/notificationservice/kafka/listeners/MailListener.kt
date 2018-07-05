package com.teamclicker.notificationservice.kafka.listeners

import com.teamclicker.notificationservice.kafka.PASSWORD_RESET_EMAIL_TOPIC
import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import com.teamclicker.notificationservice.mail.MailService
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class MailListener(
    val mailService: MailService
) {
    init {
        logger.info { "created" }
    }

    @KafkaListener(topics = [PASSWORD_RESET_EMAIL_TOPIC])
    fun passwordReset(@Payload data: PasswordResetEmailKDTO) {
        logger.info { "Received $data" }
        mailService.send(data)
    }

    companion object: KLogging()
}