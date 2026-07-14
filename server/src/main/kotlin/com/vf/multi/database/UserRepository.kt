package com.vf.multi.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.vf.multi.config.AppConfig
import com.vf.multi.models.User
import java.time.LocalDateTime

class UserRepository {
    private val mongoClient: MongoClient = try {
        MongoClients.create(AppConfig.mongodbUri)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException(
            "Invalid MONGODB_URI. If username/password contains '@' or ':', URL-encode them (e.g. '@' -> %40, ':' -> %3A).",
            e
        )
    }
    private val database = mongoClient.getDatabase(AppConfig.databaseName)
    private val usersCollection = database.getCollection("users", User::class.java)

    fun findByEmail(email: String): User? {
        return try {
            usersCollection.find(Filters.eq("email", email)).first()
        } catch (e: Exception) {
            println("Error finding user: ${e.message}")
            null
        }
    }

    fun findById(id: String): User? {
        return try {
            usersCollection.find(Filters.eq("_id", id)).first()
        } catch (e: Exception) {
            println("Error finding user by id: ${e.message}")
            null
        }
    }

    fun create(user: User): Boolean {
        return try {
            usersCollection.insertOne(user)
            true
        } catch (e: Exception) {
            println("Error creating user: ${e.message}")
            false
        }
    }

    fun updateResetToken(email: String, token: String, expiryTime: String): Boolean {
        return try {
            val result = usersCollection.updateOne(
                Filters.eq("email", email),
                listOf(
                    Updates.set("resetToken", token),
                    Updates.set("resetTokenExpiry", expiryTime),
                    Updates.set("updatedAt", LocalDateTime.now().toString())
                )
            )
            result.modifiedCount > 0
        } catch (e: Exception) {
            println("Error updating reset token: ${e.message}")
            false
        }
    }

    fun resetPassword(token: String, passwordHash: String): Boolean {
        return try {
            val result = usersCollection.updateOne(
                Filters.eq("resetToken", token),
                listOf(
                    Updates.set("passwordHash", passwordHash),
                    Updates.unset("resetToken"),
                    Updates.unset("resetTokenExpiry"),
                    Updates.set("updatedAt", LocalDateTime.now().toString())
                )
            )
            result.modifiedCount > 0
        } catch (e: Exception) {
            println("Error resetting password: ${e.message}")
            false
        }
    }

    fun findByResetToken(token: String): User? {
        return try {
            usersCollection.find(Filters.eq("resetToken", token)).first()
        } catch (e: Exception) {
            println("Error finding user by reset token: ${e.message}")
            null
        }
    }

    fun close() {
        mongoClient.close()
    }
}

