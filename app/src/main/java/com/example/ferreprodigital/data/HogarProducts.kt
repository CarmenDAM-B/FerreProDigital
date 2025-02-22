package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría.
fun getHogarProducts(): List<Product> {
    return listOf(
            Product("Silla de madera", 29.99, R.drawable.sillamadera),
            Product("Mesa plegable", 49.99, R.drawable.mesaplegable),
            Product("Estantería metálica", 59.99, R.drawable.estanteriametalica)
    )
}
