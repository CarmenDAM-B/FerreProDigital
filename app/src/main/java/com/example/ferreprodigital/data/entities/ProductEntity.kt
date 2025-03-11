package com.example.ferreprodigital.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * ProductEntity representa la entidad de producto en la base de datos.
 * Cada objeto de ProductEntity se almacena como una fila en la tabla "Productos".
 *
 * Se establece una relación de clave foránea con CategoryEntity, lo que significa
 * que cada producto pertenece a una categoría. Si se elimina una categoría,
 * todos los productos asociados se eliminarán automáticamente (onDelete = CASCADE).
 */

@Entity(tableName = "Productos",
    foreignKeys = [ForeignKey(
        // Entidad padre: CategoryEntity
        entity = CategoryEntity::class,
        // Columna primaria en CategoryEntity
        parentColumns = ["idCategoria"],
        // Columna en ProductEntity que hace referencia a la categoría
        childColumns = ["idCategoria"],
        // Si se elimina la categoría, se eliminan los productos asociados
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["idCategoria"])]
)
data class ProductEntity(
    // Identificador único del producto, generado automáticamente.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProducto")
    val productId: Int=0,

    @ColumnInfo(name = "Producto")
    var name: String,

    @ColumnInfo(name = "Precio")
    var price: Double,

    @ColumnInfo(name = "ImagenProducto")
    var imageResId: Int,

    // Clave foránea que relaciona el producto con una categoría.
    // Debe coincidir con la columna "idCategoria" de la entidad CategoryEntity.
    @ColumnInfo(name = "idCategoria")
    var categoryId: Int
)