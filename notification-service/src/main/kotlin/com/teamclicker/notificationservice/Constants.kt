package com.teamclicker.notificationservice


object Constants {
    const val JWT_HEADER_NAME = "Authorization"
    const val JWT_TOKEN_PREFIX = "Bearer "

    const val JWT_PUBLIC_KEY_NAME = "classpath:jwt_public_key.der"

    const val MAIL_TEMPLATE_VARIABLE_START = "[["
    const val MAIL_TEMPLATE_VARIABLE_END = "]]"
    const val MAIL_TEMPLATES_PATH = "classpath:mail_templates"
}