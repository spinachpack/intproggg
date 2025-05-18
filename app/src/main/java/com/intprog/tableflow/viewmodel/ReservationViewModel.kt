package com.intprog.tableflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intprog.tableflow.model.Reservation
import com.intprog.tableflow.model.ReservationStatus
import com.intprog.tableflow.repository.ReservationRepository
import com.intprog.tableflow.repository.RestaurantRepository
import kotlinx.coroutines.launch
import java.util.*

class ReservationViewModel : ViewModel() {
    private val repository = ReservationRepository()
    private val restaurantRepository = RestaurantRepository() // Added RestaurantRepository

    private val _userReservations = MutableLiveData<List<Reservation>>()
    val userReservations: LiveData<List<Reservation>> = _userReservations

    private val _reservation = MutableLiveData<Reservation?>()
    val reservation: LiveData<Reservation?> = _reservation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getUserReservations(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val reservations = repository.getUserReservations(email)
                _userReservations.value = reservations
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getReservationById(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val reservation = repository.getReservationById(id)
                _reservation.value = reservation
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createReservation(restaurantId: String, userEmail: String, date: String, time: String, guests: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // First get the restaurant to include its name in the reservation
                val restaurant = restaurantRepository.getRestaurantById(restaurantId) // Using restaurantRepository instead of repository
                if (restaurant == null) {
                    _error.value = "Restaurant not found"
                    return@launch
                }

                val reservation = Reservation(
                    id = UUID.randomUUID().toString(),
                    restaurantId = restaurantId,
                    restaurantName = restaurant.name,
                    userEmail = userEmail,
                    date = date,
                    time = time,
                    guests = guests,
                    status = ReservationStatus.RESERVED,
                    createdAt = System.currentTimeMillis()
                )

                val createdReservation = repository.createReservation(reservation)
                _reservation.value = createdReservation
                _error.value = null

                // Refresh user reservations
                getUserReservations(userEmail)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateReservationStatus(id: String, status: ReservationStatus, userEmail: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val updatedReservation = repository.updateReservationStatus(id, status)
                _reservation.value = updatedReservation
                _error.value = null

                // Refresh user reservations
                getUserReservations(userEmail)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteReservation(id: String, userEmail: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val success = repository.deleteReservation(id)
                if (success) {
                    _reservation.value = null

                    // Refresh user reservations
                    getUserReservations(userEmail)
                } else {
                    _error.value = "Failed to delete reservation"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearReservation() {
        _reservation.value = null
    }
}