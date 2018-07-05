package com.teamclicker.notificationservice.mail

import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO

interface MailService {
    fun send(data: PasswordResetEmailKDTO)
}