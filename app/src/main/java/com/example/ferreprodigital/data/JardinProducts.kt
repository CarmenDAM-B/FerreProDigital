package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría.
fun getJardinProducts(): List<Product> {
    return listOf(
        Product("Tijeras de podar", 19.99, R.drawable.tijeraspodar),
        Product("Manguera de riego", 14.99, R.drawable.manguerariego),
        Product("Regadera para plantas", 9.99, R.drawable.regaderaplantas)
    )
}
