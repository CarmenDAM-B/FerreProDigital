package com.example.ferreprodigital.viewmodel

import androidx.lifecycle.*
import com.example.ferreprodigital.data.entities.OrderEntity
import com.example.ferreprodigital.data.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _orderInserted = MutableLiveData<Boolean>()
    val orderInserted: LiveData<Boolean> get() = _orderInserted

    fun insertOrder(order: OrderEntity) {
        viewModelScope.launch {
            try {
                repository.insertOrder(order)
                _orderInserted.value = true
            } catch (e: Exception) {
                _orderInserted.value = false
            }
        }
    }
}
