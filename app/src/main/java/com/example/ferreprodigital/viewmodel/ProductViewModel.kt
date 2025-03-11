package com.example.ferreprodigital.viewmodel

import androidx.lifecycle.*
import com.example.ferreprodigital.data.entities.ProductEntity
import com.example.ferreprodigital.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productList = MutableLiveData<List<ProductEntity>>()
    val productList: LiveData<List<ProductEntity>> = _productList

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = repository.getAllProducts()
                _productList.value = products
            } catch (e: Exception) {
                // Manejar el error si es necesario
            }
        }
    }

    fun loadProductsByCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                val products = repository.getProductsByCategory(categoryId)
                _productList.value = products
            } catch (e: Exception) {
                // Manejar el error si es necesario
            }
        }
    }

    fun addProduct(producto: String, precio: Double, imagenProducto: Int, idCategoria: Int) {
        viewModelScope.launch {
            try {
                val product = ProductEntity(
                    name = producto,
                    price = precio,
                    imageResId = imagenProducto,
                    categoryId = idCategoria
                )
                repository.insertProduct(product)
                loadProducts()
            } catch (e: Exception) {
                // Manejar el error si es necesario
            }
        }
    }

    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product)
                loadProducts()
            } catch (e: Exception) {
                // Manejar el error si es necesario
            }
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(product)
                loadProducts()
            } catch (e: Exception) {
                // Manejar el error si es necesario
            }
        }
    }

    // Nuevos métodos para trabajar con categorías
    fun getProductsWithCategories() {
        viewModelScope.launch {
            val productsWithCategories = repository.getProductsWithCategoryNamesList()
            // Aquí podrías transformar los datos si es necesario
        }
    }
}
