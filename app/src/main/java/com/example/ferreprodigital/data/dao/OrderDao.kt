package com.example.ferreprodigital.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ferreprodigital.data.entities.OrderEntity

/**
* OrderDao define las operaciones para gestionar las órdenes de compra en la base de datos.
* Permite insertar una nueva orden y consultar todas las órdenes, ordenándolas por el identificador.
*/

@Dao
interface OrderDao {
    // Inserta una orden en la tabla "Ordenes".
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long


    // Obtiene todas las órdenes de la tabla "Ordenes" ordenadas en forma descendente por id.
    @Query("SELECT * FROM Ordenes ORDER BY idOrden DESC")
    suspend fun getAllOrders(): List<OrderEntity>
}
