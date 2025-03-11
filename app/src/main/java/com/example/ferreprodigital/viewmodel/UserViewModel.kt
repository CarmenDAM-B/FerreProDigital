package com.example.ferreprodigital.viewmodel

import androidx.lifecycle.*
import com.example.ferreprodigital.data.entities.UserEntity
import com.example.ferreprodigital.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<UserEntity?>()
    val currentUser: LiveData<UserEntity?> get() = _currentUser

    // LiveData para notificar el resultado del registro
    private val _userInserted = MutableLiveData<Boolean>()
    val userInserted: LiveData<Boolean> get() = _userInserted

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            _currentUser.value = repository.getUserByUsername(username)
        }
    }

    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            try {
                repository.insertUser(user)
                _userInserted.value = true
            } catch (e: Exception) {
                _userInserted.value = false
            }
        }
    }
}
