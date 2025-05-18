package com.intprog.tableflow.api

import com.intprog.tableflow.model.Restaurant
import retrofit2.Response
import retrofit2.http.*

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getAllRestaurants(): Response<List<Restaurant>>

    @GET("restaurants/{id}")
    suspend fun getRestaurantById(@Path("id") id: String): Response<Restaurant>

    @POST("restaurants")
    suspend fun createRestaurant(@Body restaurant: Restaurant): Response<Restaurant>

    @PUT("restaurants/{id}")
    suspend fun updateRestaurant(
        @Path("id") id: String,
        @Body restaurant: Restaurant
    ): Response<Restaurant>

    @DELETE("restaurants/{id}")
    suspend fun deleteRestaurant(@Path("id") id: String): Response<Void>
}