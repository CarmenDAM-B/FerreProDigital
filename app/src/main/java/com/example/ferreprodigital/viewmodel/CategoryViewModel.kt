package com.example.ferreprodigital.viewmodel

import androidx.lifecycle.*
import com.example.ferreprodigital.data.entities.CategoryEntity
import com.example.ferreprodigital.data.repository.CategoryRepository
import kotlinx.coroutines.launch

/**
 * CategoryViewModel se encarga de gestionar la información de las categorías.
 * Utiliza el CategoryRepository para obtener los datos de la base de datos y los expone
 * a la UI a través de LiveData.
 */

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {
    // LiveData que contiene la lista de categorías obtenidas desde la base de datos.
    private val _categoryList = MutableLiveData<List<CategoryEntity>>()
    val categoryList: LiveData<List<CategoryEntity>> get() = _categoryList

    fun loadCategories() {
        viewModelScope.launch {
            _categoryList.value = repository.getAll()
        }
    }
}
