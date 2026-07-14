package com.vf.multi.config

import io.github.cdimascio.dotenv.Dotenv

object AppConfig {
    private val rootDotenv: Dotenv? = runCatching {
        Dotenv.configure().ignoreIfMissing().load()
    }.getOrNull()

    private val serverDotenv: Dotenv? = runCatching {
        Dotenv.configure().directory("server").ignoreIfMissing().load()
    }.getOrNull()

    private fun configValue(name: String): String? {
        return System.getenv(name)
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: rootDotenv?.get(name)?.trim()?.takeIf { it.isNotEmpty() }
            ?: serverDotenv?.get(name)?.trim()?.takeIf { it.isNotEmpty() }
    }

    val mongodbUri: String = configValue("MONGODB_URI") ?: "mongodb://localhost:27017"
    val databaseName: String = configValue("DATABASE_NAME") ?: "multiplatform_db"
    val jwtSecret: String = configValue("JWT_SECRET") ?: "your-secret-key-change-in-production"
    val jwtExpiration: Long = 86400000 // 24 hours in milliseconds
    val emailSender: String = configValue("EMAIL_SENDER") ?: "noreply@multiplatform.com"
    val emailPassword: String = configValue("EMAIL_PASSWORD") ?: ""
}