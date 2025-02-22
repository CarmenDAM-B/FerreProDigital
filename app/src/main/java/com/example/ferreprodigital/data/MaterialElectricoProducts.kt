package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría.
fun getMaterialElectricoProducts(): List<Product> {
    return listOf(
        Product("Cable eléctrico", 5.99, R.drawable.cableelectrico),
        Product("Enchufe doble", 3.99, R.drawable.enchufedoble),
        Product("Interruptor", 2.49, R.drawable.interruptor)
    )
}
