package com.teamclicker.notificationservice.mappers

import com.teamclicker.notificationservice.security.AuthenticationMethod
import com.teamclicker.notificationservice.security.JWTData
import io.jsonwebtoken.Claims
import io.jsonwebtoken.RequiredTypeException
import org.springframework.stereotype.Service

@Service
class ClaimsToJWTDataMapper : AbstractMapper<Claims, JWTData>() {
    override fun parse(from: Claims): JWTData {
        return JWTData(
            accountId = getAccountId(from),
            authenticationMethod = from.get("authenticationMethod", String::class.java)
                .let { AuthenticationMethod.valueOf(it) },
            roles = from.get("roles", userRolesListType).toSet()
        )
    }

    private fun getAccountId(from: Claims): Long {
        return try {
            from.get("accountId", java.lang.Long::class.java).toLong()
        } catch (e: RequiredTypeException) {
            from.get("accountId", java.lang.Integer::class.java).toLong()
        }
    }

    companion object {
        val userRolesListType = arrayListOf<String>()::class.java
    }
}