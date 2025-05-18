package com.intprog.tableflow.api

import com.intprog.tableflow.model.PreOrder
import retrofit2.Response
import retrofit2.http.*

interface PreOrderApiService {
    @GET("preorders")
    suspend fun getAllPreOrders(): Response<List<PreOrder>>

    @GET("preorders/{id}")
    suspend fun getPreOrderById(@Path("id") id: String): Response<PreOrder>

    @GET("preorders/reservation/{reservationId}")
    suspend fun getPreOrderByReservationId(@Path("reservationId") reservationId: String): Response<PreOrder>

    @POST("preorders")
    suspend fun createPreOrder(@Body preOrder: PreOrder): Response<PreOrder>

    @PUT("preorders/{id}")
    suspend fun updatePreOrder(
        @Path("id") id: String,
        @Body preOrder: PreOrder
    ): Response<PreOrder>

    @DELETE("preorders/{id}")
    suspend fun deletePreOrder(@Path("id") id: String): Response<Void>
}