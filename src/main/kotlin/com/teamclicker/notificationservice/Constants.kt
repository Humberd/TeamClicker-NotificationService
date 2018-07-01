package com.teamclicker.notificationservice


object Constants {
    const val JWT_HEADER_NAME = "Authorization"
    const val JWT_TOKEN_PREFIX = "Bearer "

    val JWT_PUBLIC_KEY_NAME = "classpath:jwt_public_key.der"
    val JWT_EXPIRATION_TIME = 864000000 // 10 days
}