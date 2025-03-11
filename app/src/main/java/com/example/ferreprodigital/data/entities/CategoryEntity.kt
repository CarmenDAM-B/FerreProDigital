package com.example.ferreprodigital.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * CategoryEntity representa la entidad de categoría en la base de datos.
 * Cada instancia de CategoryEntity se almacena como una fila en la tabla "Categorias".
 *
 * Esta entidad contiene la información básica de cada categoría, que se usará para clasificar
 * los productos en la aplicación.
 */

@Entity(tableName = "Categorias")
data class CategoryEntity(
    // Identificador único, se genera de forma automática.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCategoria")
    val categoryId: Int = 0,

    @ColumnInfo(name = "Categoria")
    val name: String,

    @ColumnInfo(name = "ImagenCategoria")
    val imageResId: Int
)
