package com.example.ferreprodigital.data

import com.example.ferreprodigital.model.Product
import android.util.Log

object Cart {
    //Cambio a un mapa para manejar productos por nombre
    private val items = mutableMapOf<String, Product>()

    // Agregar producto al carrito
    fun addItem(product: Product) {
        try {
            Log.d("Cart", "Intentando agregar producto: ${product.name}")
            
            if (items.containsKey(product.name)) {
                val currentProduct = items[product.name]!!
                Log.d("Cart", "Producto existente, cantidad actual: ${currentProduct.cantidad}")
                
                // Crear nuevo producto con cantidad incrementada
                val updatedProduct = Product(
                    name = currentProduct.name,
                    price = currentProduct.price,
                    imageResId = currentProduct.imageResId,
                    cantidad = currentProduct.cantidad + 1
                )
                items[product.name] = updatedProduct
                
            } else {
                Log.d("Cart", "Nuevo producto, cantidad inicial: 1")
                // Asegurarse de que el nuevo producto tenga cantidad = 1
                val newProduct = Product(
                    name = product.name,
                    price = product.price,
                    imageResId = product.imageResId,
                    cantidad = 1
                )
                items[product.name] = newProduct
            }
            
            Log.d("Cart", "Producto agregado exitosamente")
        } catch (e: Exception) {
            Log.e("Cart", "Error al agregar producto", e)
            throw e  // Re-lanzar la excepción para manejarla en la UI
        }
    }

    // Eliminar producto del carrito
    fun removeItem(product: Product) {
        if (items.containsKey(product.name)) {
            val currentProduct = items[product.name]!!
            if (currentProduct.cantidad > 1) {
                items[product.name] = currentProduct.copy(cantidad = currentProduct.cantidad - 1)
            } else {
                // Si la cantidad llega a 0 o 1, eliminar el producto
                items.remove(product.name)
            }
        }
    }

    // Obtener la lista de productos en el carrito
    fun getItems(): List<Product> {
        return items.values.toList()
    }

    // Obtener el total a pagar
    fun getTotal(): Double {
        return items.values.sumOf { it.price * it.cantidad }
    }

    // Vaciar el carrito
    fun clearCart() {
        items.clear()
    }
}
