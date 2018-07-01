package com.teamclicker.notificationservice.configs

import com.teamclicker.notificationservice.resolvers.JWTDataResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    val jwtDataResolver: JWTDataResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtDataResolver)
    }
}