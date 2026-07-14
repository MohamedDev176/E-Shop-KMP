package com.vf.multi.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    val id: String = org.bson.types.ObjectId().toString(),
    val email: String,
    val name: String,
    val passwordHash: String,
    val createdAt: String = LocalDateTime.now().toString(),
    val updatedAt: String = LocalDateTime.now().toString(),
    val isActive: Boolean = true,
    val resetToken: String? = null,
    val resetTokenExpiry: String? = null
)

@Serializable
data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class ForgotPasswordRequest(
    val email: String
)

@Serializable
data class ResetPasswordRequest(
    val token: String,
    val newPassword: String
)

@Serializable
data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val user: UserResponse? = null
)

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val name: String,
    val createdAt: String
)

@Serializable
data class MessageResponse(
    val success: Boolean,
    val message: String
)

