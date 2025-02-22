package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría.
fun getFontaneriaProducts(): List<Product> {
    return listOf(
        Product("Tubería PVC", 12.99, R.drawable.tuberia),
        Product("Llave de paso", 8.99, R.drawable.llavepaso),
        Product("Codo de cobre", 6.49, R.drawable.codocobre)
    )
}
