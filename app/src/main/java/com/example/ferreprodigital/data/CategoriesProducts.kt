package com.example.ferreprodigital.data

import com.example.ferreprodigital.data.models.Product
import com.example.ferreprodigital.R

// Devuelve productos para la categoría Fontaneria.
fun getFontaneriaProducts(): List<Product> {
    return listOf(
        Product(
            name = "Tubería PVC",
            price = 12.99,
            imageResId = R.drawable.tuberia),
        Product(
            name = "Llave de paso",
            price = 8.99,
            imageResId = R.drawable.llavepaso),
        Product(
            name = "Codo de cobre",
            price = 6.49,
            imageResId = R.drawable.codocobre)
    )
}

// Devuelve productos para la categoría Herramientas.
fun getHerramientasProducts(): List<Product> {
    return listOf(
        Product(
            name = "Taladro",
            price = 99.99,
            imageResId = R.drawable.taladro),
        Product(
            name = "Martillo",
            price = 19.99,
            imageResId = R.drawable.martillo),
        Product(
            name = "Sierra",
            price = 49.99,
            imageResId = R.drawable.sierra)
    )
}

// Devuelve productos para la categoría Hogar.
fun getHogarProducts(): List<Product> {
    return listOf(
        Product(
            name = "Silla de madera",
            price = 29.99,
            imageResId = R.drawable.sillamadera),
        Product(
            name = "Mesa plegable",
            price = 49.99,
            imageResId = R.drawable.mesaplegable),
        Product(
            name = "Estantería metálica",
            price = 59.99,
            imageResId = R.drawable.estanteriametalica)
    )
}

// Devuelve productos para la categoría Jardin.
fun getJardinProducts(): List<Product> {
    return listOf(
        Product(
            name = "Tijeras de podar",
            price = 19.99,
            imageResId = R.drawable.tijeraspodar),
        Product(
            name = "Manguera de riego",
            price = 14.99,
            imageResId = R.drawable.manguerariego),
        Product(
            name = "Regadera para plantas",
            price = 9.99,
            imageResId = R.drawable.regaderaplantas)
    )
}

// Devuelve productos para la categoría Material Electrico.
fun getMaterialElectricoProducts(): List<Product> {
    return listOf(
        Product(
            name = "Cable eléctrico",
            price = 5.99,
            imageResId = R.drawable.cableelectrico),
        Product(
            name = "Enchufe doble",
            price = 3.99,
            imageResId = R.drawable.enchufedoble),
        Product(
            name = "Interruptor",
            price = 2.49,
            imageResId = R.drawable.interruptor)
    )
}

// Devuelve productos para la categoría Materiales de Obra.
fun getMaterialesObraProducts(): List<Product> {
    return listOf(
        Product(
            name = "Cemento",
            price = 8.49,
            imageResId = R.drawable.cemento),
        Product(
            name = "Ladrillos",
            price = 0.99,
            imageResId = R.drawable.ladrillos),
        Product(
            name = "Arena gruesa",
            price = 5.99,
            imageResId = R.drawable.arenagruesa)
    )
}

// Devuelve productos para la categoría Ropa de Trabajo.
fun getRopaTrabajoProducts(): List<Product> {
    return listOf(
        Product(
            name = "Casco de seguridad",
            price = 15.99,
            imageResId = R.drawable.cascoseguridad),
        Product(
            name = "Chaleco reflectante",
            price = 9.99,
            imageResId = R.drawable.chalecoreflectante),
        Product(
            name = "Guantes de protección",
            price = 6.49,
            imageResId = R.drawable.guantesproteccion)
    )
}

// Devuelve productos para la categoría Bricolaje.
fun getBricolajeProducts(): List<Product> {
    return listOf(
        Product(
            name = "Kit bricolaje1",
            price = 12.99,
            imageResId = R.drawable.kit_bricolaje1),
        Product(
            name = "Kit bricolaje2",
            price = 8.99,
            imageResId = R.drawable.kit_bricolaje2),
        Product(
            name = "Kit bricolaje3",
            price = 6.49,
            imageResId = R.drawable.kit_bricolaje3)
    )
}
