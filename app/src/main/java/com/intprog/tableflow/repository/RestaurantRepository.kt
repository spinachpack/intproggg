package com.intprog.tableflow.repository

import com.intprog.tableflow.api.RetrofitClient
import com.intprog.tableflow.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestaurantRepository {
    private val apiService = RetrofitClient.restaurantApiService

    suspend fun getAllRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getAllRestaurants()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch restaurants: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getRestaurantById(id: String): Restaurant? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getRestaurantById(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun createRestaurant(restaurant: Restaurant): Restaurant {
        return withContext(Dispatchers.IO) {
            val response = apiService.createRestaurant(restaurant)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to create restaurant: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun updateRestaurant(id: String, restaurant: Restaurant): Restaurant {
        return withContext(Dispatchers.IO) {
            val response = apiService.updateRestaurant(id, restaurant)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to update restaurant: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun deleteRestaurant(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = apiService.deleteRestaurant(id)
            response.isSuccessful
        }
    }
}