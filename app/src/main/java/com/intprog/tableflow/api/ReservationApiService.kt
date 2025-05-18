package com.intprog.tableflow.api

import com.intprog.tableflow.model.Reservation
import com.intprog.tableflow.model.ReservationStatus
import retrofit2.Response
import retrofit2.http.*

interface ReservationApiService {
    @GET("reservations")
    suspend fun getAllReservations(): Response<List<Reservation>>

    @GET("reservations/{id}")
    suspend fun getReservationById(@Path("id") id: String): Response<Reservation>

    @GET("reservations/user/{email}")
    suspend fun getUserReservations(@Path("email") email: String): Response<List<Reservation>>

    @POST("reservations")
    suspend fun createReservation(@Body reservation: Reservation): Response<Reservation>

    @PUT("reservations/{id}")
    suspend fun updateReservation(
        @Path("id") id: String,
        @Body reservation: Reservation
    ): Response<Reservation>

    @PATCH("reservations/{id}/status")
    suspend fun updateReservationStatus(
        @Path("id") id: String,
        @Body status: Map<String, ReservationStatus>
    ): Response<Reservation>

    @DELETE("reservations/{id}")
    suspend fun deleteReservation(@Path("id") id: String): Response<Void>
}