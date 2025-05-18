package com.intprog.tableflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intprog.tableflow.model.User
import com.intprog.tableflow.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun registerUser(user: User) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val registeredUser = repository.registerUser(user)
                _currentUser.value = registeredUser
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val loggedInUser = repository.loginUser(email, password)
                _currentUser.value = loggedInUser
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserByEmail(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val user = repository.getUserByEmail(email)
                _currentUser.value = user
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateUser(user: User) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val updatedUser = repository.updateUser(user.email, user)
                _currentUser.value = updatedUser
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePassword(email: String, oldPassword: String, newPassword: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val success = repository.updatePassword(email, oldPassword, newPassword)
                if (!success) {
                    _error.value = "Failed to update password"
                } else {
                    _error.value = null
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun clearError() {
        _error.value = null
    }
}