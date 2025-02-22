package com.example.ferreprodigital.data

import com.example.ferreprodigital.R
import com.example.ferreprodigital.model.Product

// Devuelve productos para la categoría.
fun getBricolajeProducts(): List<Product> {
    return listOf(
        Product("Kit bricolaje1", 12.99, R.drawable.kit_bricolaje1),
        Product("Kit bricolaje2", 8.99, R.drawable.kit_bricolaje2),
        Product("Kit bricolaje3", 6.49, R.drawable.kit_bricolaje3)
    )
}
