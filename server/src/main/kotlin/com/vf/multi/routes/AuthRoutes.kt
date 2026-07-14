package com.vf.multi.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.vf.multi.database.UserRepository
import com.vf.multi.models.*
import com.vf.multi.services.EmailService
import com.vf.multi.utils.AuthUtils
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun Route.authRoutes(userRepository: UserRepository, emailService: EmailService) {

    post("/api/register") {
        try {
            val request = call.receive<RegisterRequest>()

            // Validate input
            if (request.email.isBlank() || request.password.isBlank() || request.name.isBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    MessageResponse(false, "Email, name, and password are required")
                )
                return@post
            }

            // Check if user already exists
            val existingUser = userRepository.findByEmail(request.email)
            if (existingUser != null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    MessageResponse(false, "User already exists with this email")
                )
                return@post
            }

            // Hash password
            val passwordHash = AuthUtils.hashPassword(request.password)

            // Create new user
            val newUser = User(
                email = request.email,
                name = request.name,
                passwordHash = passwordHash
            )

            // Save to database
            val created = userRepository.create(newUser)
            if (created) {
                val token = AuthUtils.generateToken(newUser.id, newUser.email)
                call.respond(
                    HttpStatusCode.Created,
                    AuthResponse(
                        success = true,
                        message = "User registered successfully",
                        token = token,
                        user = UserResponse(
                            id = newUser.id,
                            email = newUser.email,
                            name = newUser.name,
                            createdAt = newUser.createdAt
                        )
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    MessageResponse(false, "Failed to create user")
                )
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                MessageResponse(false, "Invalid request: ${e.message}")
            )
        }
    }

    post("/api/login") {
        try {
            val request = call.receive<LoginRequest>()

            // Validate input
            if (request.email.isBlank() || request.password.isBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    MessageResponse(false, "Email and password are required")
                )
                return@post
            }

            // Find user by email
            val user = userRepository.findByEmail(request.email)
            if (user == null) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    MessageResponse(false, "Invalid email or password")
                )
                return@post
            }

            // Verify password
            val isPasswordValid = AuthUtils.verifyPassword(request.password, user.passwordHash)
            if (!isPasswordValid) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    MessageResponse(false, "Invalid email or password")
                )
                return@post
            }

            // Generate JWT token
            val token = AuthUtils.generateToken(user.id, user.email)
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(
                    success = true,
                    message = "Login successful",
                    token = token,
                    user = UserResponse(
                        id = user.id,
                        email = user.email,
                        name = user.name,
                        createdAt = user.createdAt
                    )
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                MessageResponse(false, "Invalid request: ${e.message}")
            )
        }
    }

    post("/api/forgot-password") {
        try {
            val request = call.receive<ForgotPasswordRequest>()

            // Validate input
            if (request.email.isBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    MessageResponse(false, "Email is required")
                )
                return@post
            }

            // Find user by email
            val user = userRepository.findByEmail(request.email)
            if (user == null) {
                // For security, don't reveal if email exists
                call.respond(
                    HttpStatusCode.OK,
                    MessageResponse(
                        success = true,
                        message = "If an account exists with this email, a password reset link will be sent"
                    )
                )
                return@post
            }

            // Generate reset token
            val resetToken = AuthUtils.generateResetToken()
            val expiryTime = LocalDateTime.now().plus(1, ChronoUnit.HOURS).toString()

            // Update user with reset token
            val updated = userRepository.updateResetToken(request.email, resetToken, expiryTime)

            if (updated) {
                // Send email with reset link
                val emailSent = emailService.sendPasswordResetEmail(request.email, resetToken)

                if (emailSent) {
                    call.respond(
                        HttpStatusCode.OK,
                        MessageResponse(
                            success = true,
                            message = "Password reset link sent to your email"
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        MessageResponse(false, "Failed to send reset email")
                    )
                }
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    MessageResponse(false, "Failed to initiate password reset")
                )
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                MessageResponse(false, "Invalid request: ${e.message}")
            )
        }
    }

    post("/api/reset-password") {
        try {
            val request = call.receive<ResetPasswordRequest>()

            // Validate input
            if (request.token.isBlank() || request.newPassword.isBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    MessageResponse(false, "Token and new password are required")
                )
                return@post
            }

            // Find user by reset token
            val user = userRepository.findByResetToken(request.token)
            if (user == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    MessageResponse(false, "Invalid or expired reset token")
                )
                return@post
            }

            // Check if token has expired
            if (user.resetTokenExpiry != null) {
                val expiryTime = LocalDateTime.parse(user.resetTokenExpiry)
                if (LocalDateTime.now().isAfter(expiryTime)) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        MessageResponse(false, "Reset token has expired")
                    )
                    return@post
                }
            }

            // Hash new password
            val newPasswordHash = AuthUtils.hashPassword(request.newPassword)

            // Update user password
            val updated = userRepository.resetPassword(request.token, newPasswordHash)
            if (updated) {
                call.respond(
                    HttpStatusCode.OK,
                    MessageResponse(true, "Password reset successfully")
                )
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    MessageResponse(false, "Failed to reset password")
                )
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                MessageResponse(false, "Invalid request: ${e.message}")
            )
        }
    }
}

