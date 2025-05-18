package com.intprog.tableflow.api

import com.intprog.tableflow.model.MenuItem
import retrofit2.Response
import retrofit2.http.*

interface MenuApiService {
    @GET("menu")
    suspend fun getAllMenuItems(): Response<List<MenuItem>>

    @GET("menu/restaurant/{restaurantId}")
    suspend fun getMenuByRestaurantId(@Path("restaurantId") restaurantId: String): Response<List<MenuItem>>

    @GET("menu/{id}")
    suspend fun getMenuItemById(@Path("id") id: String): Response<MenuItem>

    @POST("menu")
    suspend fun createMenuItem(@Body menuItem: MenuItem): Response<MenuItem>

    @PUT("menu/{id}")
    suspend fun updateMenuItem(
        @Path("id") id: String,
        @Body menuItem: MenuItem
    ): Response<MenuItem>

    @DELETE("menu/{id}")
    suspend fun deleteMenuItem(@Path("id") id: String): Response<Void>
}