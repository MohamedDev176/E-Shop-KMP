package com.vf.multi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.vf.multi.database.UserRepository
import com.vf.multi.routes.authRoutes
import com.vf.multi.services.EmailService

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val userRepository = UserRepository()
    val emailService = EmailService()

    install(ContentNegotiation) { json() }

    routing {
        get("/") {
            call.respondText("MultiPlatform API Server is running")
        }

        get("/health") {
            call.respondText("OK")
        }

        authRoutes(userRepository, emailService)
    }
}