package com.intprog.tableflow.api

import com.intprog.tableflow.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {
    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<User>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>

    @POST("users/login")
    suspend fun loginUser(@Body credentials: Map<String, String>): Response<User>

    @PUT("users/{email}")
    suspend fun updateUser(
        @Path("email") email: String,
        @Body user: User
    ): Response<User>

    @PATCH("users/{email}/password")
    suspend fun updatePassword(
        @Path("email") email: String,
        @Body passwordData: Map<String, String>
    ): Response<User>

    @DELETE("users/{email}")
    suspend fun deleteUser(@Path("email") email: String): Response<Void>
}