package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría.
fun getMaterialesObraProducts(): List<Product> {
    return listOf(
        Product("Cemento", 8.49, R.drawable.cemento),
        Product("Ladrillos", 0.99, R.drawable.ladrillos),
        Product("Arena gruesa", 5.99, R.drawable.arenagruesa)
    )
}
