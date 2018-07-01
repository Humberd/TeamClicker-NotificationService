package com.teamclicker.notificationservice.kafka.dto

data class PasswordResetEmailKDTO(
    val email: String = "",
    val token: String = ""
)