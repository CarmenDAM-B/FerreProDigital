package com.example.ferreprodigital.model

import android.os.Parcel
import android.os.Parcelable

// Clase de datos que representa un producto
data class Product(
    val name: String,
    val price: Double,
    val imageResId: Int,
    var cantidad: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt()
    )
    // Método que escribe los datos del objeto en el Parcel (para pasar entre actividades)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeInt(imageResId)
        parcel.writeInt(cantidad)
    }

    // Método que crea el objeto a partir de un Parcel (para leerlo cuando se pase entre actividades)
    override fun describeContents(): Int = 0

    // Método estático para crear un objeto Product desde un Parcel
    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }
        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
