package com.example.ferreprodigital.data

import com.example.ferreprodigital.R
import com.example.ferreprodigital.model.Product

// Devuelve productos para la categoría.
fun getHerramientasProducts(): List<Product> {
    return listOf(
        Product("Taladro", 99.99, R.drawable.taladro),
        Product("Martillo", 19.99, R.drawable.martillo),
        Product("Sierra", 49.99, R.drawable.sierra)
    )
}
