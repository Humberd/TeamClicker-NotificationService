package com.teamclicker.notificationservice.kafka

import org.springframework.kafka.support.serializer.JsonDeserializer

class JsonDeserializer<T> : JsonDeserializer<T>() {
    init {
        super.addTrustedPackages("*")
    }
}