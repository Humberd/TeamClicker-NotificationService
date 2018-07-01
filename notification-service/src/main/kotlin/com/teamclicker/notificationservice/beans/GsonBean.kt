package com.teamclicker.notificationservice.beans

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teamclicker.notificationservice.serializers.DateDeserializer
import com.teamclicker.notificationservice.serializers.DateSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class GsonBean {
    @Bean
    fun gson(): Gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Date::class.java, DateSerializer())
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()
}

