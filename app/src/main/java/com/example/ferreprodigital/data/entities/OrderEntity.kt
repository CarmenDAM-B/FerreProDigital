package com.example.ferreprodigital.data.entities

import java.util.*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * OrderEntity representa la entidad para las órdenes de compra en la base de datos.
 * Cada instancia de OrderEntity se almacena como una fila en la tabla "Ordenes".
 * Esta entidad contiene el total de la compra, la dirección de entrega y la fecha.
 */
@Entity(tableName = "Ordenes")
data class OrderEntity(
    // Identificador único, se genera automáticamente.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idOrden")
    val orderId: Int = 0,

    @ColumnInfo(name = "Total")
    val totalAmount: Double,

    @ColumnInfo(name = "Direccion")
    val address: String,

    @ColumnInfo(name = "Fecha")
    val date: Date
)
