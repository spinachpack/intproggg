package com.intprog.tableflow.repository

import com.intprog.tableflow.api.RetrofitClient
import com.intprog.tableflow.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val apiService = RetrofitClient.userApiService

    suspend fun registerUser(user: User): User {
        return withContext(Dispatchers.IO) {
            val response = apiService.createUser(user)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to register user: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun loginUser(email: String, password: String): User {
        return withContext(Dispatchers.IO) {
            val credentials = mapOf("email" to email, "password" to password)
            val response = apiService.loginUser(credentials)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Login failed: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUserByEmail(email)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun updateUser(email: String, user: User): User {
        return withContext(Dispatchers.IO) {
            val response = apiService.updateUser(email, user)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to update user: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun updatePassword(email: String, oldPassword: String, newPassword: String): Boolean {
        return withContext(Dispatchers.IO) {
            val passwordData = mapOf(
                "oldPassword" to oldPassword,
                "newPassword" to newPassword
            )
            val response = apiService.updatePassword(email, passwordData)
            response.isSuccessful
        }
    }

    suspend fun deleteUser(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = apiService.deleteUser(email)
            response.isSuccessful
        }
    }
}