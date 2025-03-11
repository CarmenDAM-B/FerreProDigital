package com.example.ferreprodigital.data.repository

import com.example.ferreprodigital.data.dao.OrderDao
import com.example.ferreprodigital.data.entities.OrderEntity

/**
 * OrderRepository se encarga de gestionar las operaciones de la base de datos relacionadas con las órdenes de compra.
 * Proporciona métodos para insertar una orden y obtener todas las órdenes, encapsulando la lógica de acceso a datos.
 */

class OrderRepository(private val orderDao: OrderDao) {

    // Inserta una nueva orden en la base de datos.
    suspend fun insertOrder(order: OrderEntity): Long {
        return orderDao.insertOrder(order)
    }

    // Recupera todas las órdenes almacenadas en la base de datos, ordenadas de forma descendente por ID.
    suspend fun getAllOrders(): List<OrderEntity> {
        return orderDao.getAllOrders()
    }
}
