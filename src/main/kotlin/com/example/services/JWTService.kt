package com.example.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.*

class JWTService(
    val application: Application,
) {
    private val secret = System.getenv("SECRET")
    private val issuer = System.getenv("ISSUER")
    private val audience = System.getenv("AUDIENCE")

    val realm = System.getenv("REALM")

    val jwtVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createJWT(id: String): String? {
        return JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("id", id)
            .withExpiresAt(Date(System.currentTimeMillis() + 3000_600_000))
            .sign(Algorithm.HMAC256(secret))
    }


    fun extractId(token: String): String? {
        return try {
            val decodedJWT = jwtVerifier.verify(token) // Валидация токена
            decodedJWT.getClaim("id").asString() // Извлечение значения "id"
        } catch (e: JWTVerificationException) {
            null
        }
    }

}