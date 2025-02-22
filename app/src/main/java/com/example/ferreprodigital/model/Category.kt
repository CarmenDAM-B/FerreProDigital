package com.example.ferreprodigital.model

// Clase de datos que representa una categoría de productos
data class Category(
    // El nombre de la categoría, por ejemplo "Fontanería", "Herramientas"
    val name: String,

    // El ID del recurso de imagen asociado a la categoría
    val imageResId: Int
)