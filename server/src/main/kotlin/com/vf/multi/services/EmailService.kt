package com.vf.multi.services

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService {
    private val session = createMailSession()

    private fun createMailSession(): Session {
        val properties = Properties().apply {
            put("mail.smtp.host", "smtp.gmail.com") // or your email service
            put("mail.smtp.port", "587")
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.starttls.required", "true")
        }

        return Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(
                    System.getenv("EMAIL_SENDER") ?: "your-email@gmail.com",
                    System.getenv("EMAIL_PASSWORD") ?: "your-app-password"
                )
            }
        })
    }

    fun sendPasswordResetEmail(email: String, resetToken: String): Boolean {
        return try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(System.getenv("EMAIL_SENDER") ?: "noreply@multiplatform.com"))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(email))
                subject = "Password Reset Request"
                val resetLink = "http://localhost:3000/reset-password?token=$resetToken"
                setText(
                    """
                    |Hello,
                    |
                    |You have requested to reset your password. Click the link below to proceed:
                    |$resetLink
                    |
                    |This link will expire in 1 hour.
                    |
                    |If you didn't request this, please ignore this email.
                    |
                    |Best regards,
                    |MultiPlatform Team
                    """.trimMargin(),
                    "utf-8",
                    "html"
                )
            }

            Transport.send(message)
            true
        } catch (e: Exception) {
            println("Error sending email: ${e.message}")
            false
        }
    }
}

