package com.intprog.tableflow.repository

import com.intprog.tableflow.api.RetrofitClient
import com.intprog.tableflow.model.Reservation
import com.intprog.tableflow.model.ReservationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReservationRepository {
    private val apiService = RetrofitClient.reservationApiService

    suspend fun getAllReservations(): List<Reservation> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getAllReservations()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch reservations: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getReservationById(id: String): Reservation? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getReservationById(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun getUserReservations(email: String): List<Reservation> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUserReservations(email)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch user reservations: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun createReservation(reservation: Reservation): Reservation {
        return withContext(Dispatchers.IO) {
            val response = apiService.createReservation(reservation)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to create reservation: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun updateReservation(id: String, reservation: Reservation): Reservation {
        return withContext(Dispatchers.IO) {
            val response = apiService.updateReservation(id, reservation)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to update reservation: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun updateReservationStatus(id: String, status: ReservationStatus): Reservation {
        return withContext(Dispatchers.IO) {
            val statusMap = mapOf("status" to status)
            val response = apiService.updateReservationStatus(id, statusMap)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to update reservation status: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun deleteReservation(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = apiService.deleteReservation(id)
            response.isSuccessful
        }
    }
}