package com.vf.multi.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.vf.multi.config.AppConfig
import java.util.*

object AuthUtils {
    private val algorithm = Algorithm.HMAC256(AppConfig.jwtSecret)

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, hash: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified
    }

    fun generateToken(userId: String, email: String): String {
        val now = Date()
        val expiresAt = Date(now.time + AppConfig.jwtExpiration)

        return JWT.create()
            .withSubject(userId)
            .withClaim("email", email)
            .withIssuedAt(now)
            .withExpiresAt(expiresAt)
            .sign(algorithm)
    }

    fun verifyToken(token: String): String? {
        return try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token)
            decodedJWT.subject
        } catch (e: Exception) {
            null
        }
    }

    fun generateResetToken(): String {
        return UUID.randomUUID().toString()
    }
}

