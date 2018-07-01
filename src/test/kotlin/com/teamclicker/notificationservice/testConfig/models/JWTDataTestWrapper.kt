package com.teamclicker.notificationservice.testConfig.models

import com.teamclicker.notificationservice.security.AuthenticationMethod

data class JWTDataTestWrapper(
    val accountId: Long,
    val authenticationMethod: AuthenticationMethod,
    val roles: Set<String>,
    val token: String
)

