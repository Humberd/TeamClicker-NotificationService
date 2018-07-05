package com.teamclicker.notificationservice.kafka

const val PASSWORD_RESET_EMAIL_TOPIC = "passwordResetEmail"

enum class KafkaTopic(
    val value: String
) {
    PASSWORD_RESET_EMAIL(PASSWORD_RESET_EMAIL_TOPIC)
}

val ALL_KAFKA_TOPICS = KafkaTopic.values().map { it.value }.toTypedArray()