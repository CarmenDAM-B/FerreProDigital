package com.example.ferreprodigital.data.dao

import androidx.room.*
import com.example.ferreprodigital.data.entities.ProductEntity
import com.example.ferreprodigital.data.models.ProductWithCategory
import com.example.ferreprodigital.data.models.ProductWithCategoryName

/**
 * ProductDao define las operaciones CRUD y consultas personalizadas para la entidad ProductEntity.
 * También permite obtener productos junto con información de su categoría.
 */

@Dao
interface ProductDao {

    // Inserta un producto en la tabla "Productos".
    @Insert
    suspend fun insertProduct(product: ProductEntity): Long

    // Actualiza los datos de un producto existente en la tabla "Productos".
    @Update
    suspend fun updateProduct(product: ProductEntity)


    // Elimina un producto de la tabla "Productos".
    @Delete
    suspend fun deleteProduct(product: ProductEntity)


    // Obtiene todos los productos de la tabla "Productos".
    @Query("SELECT * FROM Productos")
    suspend fun getAllProducts(): List<ProductEntity>


    // Obtiene un producto específico basado en su identificador.
    @Query("SELECT * FROM Productos WHERE idProducto = :id")
    suspend fun getProductById(id: Int): ProductEntity?


    // Consulta para obtener todos los productos junto con el nombre de la categoría.
    // Realiza un INNER JOIN entre la tabla Productos y Categorias.
    @Query(""" SELECT P.*, C.Categoria as nombreCategoria FROM Productos P
        INNER JOIN Categorias C ON P.idCategoria = C.idCategoria """)
    suspend fun getAllProductsWithCategoryName(): List<ProductWithCategoryName>

    // Consulta transaccional para obtener productos junto con su categoría.
    @Transaction
    @Query("SELECT * FROM Productos")
    suspend fun getProductsWithCategory(): List<ProductWithCategory>

    // Obtiene los productos filtrados por el identificador de la categoría.
    @Query("SELECT * FROM Productos WHERE idCategoria = :categoryId")
    suspend fun getProductsByCategory(categoryId: Int): List<ProductEntity>
}
