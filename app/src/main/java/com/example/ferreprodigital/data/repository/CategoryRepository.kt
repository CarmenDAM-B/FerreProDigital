package com.example.ferreprodigital.data.repository

import com.example.ferreprodigital.data.dao.CategoryDao
import com.example.ferreprodigital.data.entities.CategoryEntity

/**
 * CategoryRepository actúa como intermediario entre el DAO de categorías y la capa de presentación.
 * Proporciona métodos para insertar y obtener las categorías, encapsulando la lógica de acceso a datos.
 */

class CategoryRepository(private val categoryDao: CategoryDao) {

    // Inserta una categoría en la base de datos.
    suspend fun insert(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    // Recupera todas las categorías almacenadas en la base de datos.
    suspend fun getAll(): List<CategoryEntity> {
        return categoryDao.getAllCategories()
    }
}
