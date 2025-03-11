package com.example.ferreprodigital.data.repository

import com.example.ferreprodigital.data.dao.ProductDao
import com.example.ferreprodigital.data.dao.CategoryDao
import com.example.ferreprodigital.data.entities.ProductEntity
import com.example.ferreprodigital.data.entities.CategoryEntity
import com.example.ferreprodigital.data.models.ProductWithCategory
import com.example.ferreprodigital.data.models.ProductWithCategoryName
import com.example.ferreprodigital.data.models.ProductInfo
import com.example.ferreprodigital.data.models.ProductDisplay

/**
 * ProductRepository se encarga de la gestión de productos en la base de datos.
 * Combina operaciones sobre productos y, en algunos casos, sobre categorías.
 * Proporciona métodos CRUD (crear, leer, actualizar y eliminar) y consultas personalizadas.
 */

class ProductRepository(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
) {
    // Inserta un nuevo producto en la base de datos.
    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insertProduct(product)
    }

    // Actualiza los datos de un producto existente.
    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    // Elimina un producto de la base de datos.
    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    // Recupera todos los productos almacenados.
    suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts()
    }

    //  Recupera un producto específico mediante su ID.
    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(id)
    }

    // Recupera productos junto con la información de su categoría.
    suspend fun getProductsWithCategory(): List<ProductWithCategory> {
        return productDao.getProductsWithCategory()
    }

    // Recupera productos junto con el nombre de la categoría.
    suspend fun getAllProductsWithCategoryName(): List<ProductWithCategoryName> {
        return productDao.getAllProductsWithCategoryName()
    }

    // Obtiene todas las categorías, a través del CategoryDao.
    suspend fun getAllCategories() = categoryDao.getAllCategories()

    // Recupera los productos filtrados por el ID de la categoría.
    suspend fun getProductsByCategory(categoryId: Int): List<ProductEntity> {
        return productDao.getProductsByCategory(categoryId)
    }

    // Funciones de ayuda para gestionar productos
    // Inserta un producto, buscando primero la categoría por nombre.
    // Si la categoría no se encuentra, lanza una excepción.
    suspend fun insertProductWithCategoryName(
        producto: String,
        precio: Double,
        imagenProducto: Int,
        nombreCategoria: String
    ): Long {
        // Primero buscamos la categoría por nombre
        val categories = categoryDao.getAllCategories()
        val category = categories.find { it.name == nombreCategoria }
            ?: throw IllegalArgumentException("Categoría no encontrada: $nombreCategoria")

        // Creamos y guardamos el producto
        val product = ProductEntity(
            name = producto,
            price = precio,
            imageResId = imagenProducto,
            categoryId = category.categoryId
        )
        return productDao.insertProduct(product)
    }

    // Obtener lista de nombres de categorías
    suspend fun getAllCategoryNames(): List<String> {
        return categoryDao.getAllCategories().map { it.name }
    }

    // Obtener productos con nombre de categoría de forma más amigable
    suspend fun getProductsWithCategoryNamesList(): List<ProductDisplay> {
        return getAllProductsWithCategoryName().map { 
            ProductDisplay(
                productName = it.Producto,
                price = it.Precio,
                categoryName = it.nombreCategoria
            )
        }
    }

    // Obtener categoría por nombre
    suspend fun getCategoryByName(nombreCategoria: String): CategoryEntity? {
        return categoryDao.getAllCategories().find { it.name == nombreCategoria }
    }

    // Mostrar información completa de un producto
    suspend fun getProductInfo(productId: Int): ProductInfo? {
        val productWithCategory = getProductsWithCategory()
            .find { it.product.productId == productId }
            ?: return null

        return ProductInfo(
            productName = productWithCategory.product.name,
            price = productWithCategory.product.price,
            categoryName = productWithCategory.category.name,
            imageResId = productWithCategory.product.imageResId
        )
    }
}
