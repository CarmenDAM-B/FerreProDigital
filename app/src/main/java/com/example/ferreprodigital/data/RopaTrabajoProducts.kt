package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría.
fun getRopaTrabajoProducts(): List<Product> {
    return listOf(
        Product("Casco de seguridad", 15.99, R.drawable.cascoseguridad),
        Product("Chaleco reflectante", 9.99, R.drawable.chalecoreflectante),
        Product("Guantes de protección", 6.49, R.drawable.guantesproteccion)
    )
}
