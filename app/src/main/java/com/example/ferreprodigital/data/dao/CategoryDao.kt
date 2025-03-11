package com.example.ferreprodigital.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ferreprodigital.data.entities.CategoryEntity

/**
 * CategoryDao define las operaciones de acceso a la base de datos para la entidad CategoryEntity.
 * Contiene métodos para insertar una nueva categoría y para consultar todas las categorías almacenadas.
 */

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM Categorias")
    suspend fun getAllCategories(): List<CategoryEntity>
}
