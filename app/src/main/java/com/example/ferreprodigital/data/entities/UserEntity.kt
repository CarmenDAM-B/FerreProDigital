package com.example.ferreprodigital.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserEntity representa la entidad de usuario en la base de datos.
 * Se almacena en la tabla "Usuarios" y contiene la información básica
 * del usuario, como su identificador, nombre de usuario, nombre completo,
 * email y contraseña.
 */

@Entity(tableName = "Usuarios")
data class UserEntity(
    // Identificador único para cada usuario. Se genera automáticamente.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUsuario")
    val userId: Int = 0,

    // Nombre de usuario, que se usará para la autenticación.
    @ColumnInfo(name = "Usuario")
    val username: String,

    // Nombre o alias del usuario.
    @ColumnInfo(name = "NombreUsuario")
    val name: String,

    // Dirección de correo electrónico del usuario.
    @ColumnInfo(name = "Email")
    val email: String,

    // Contraseña del usuario.
    @ColumnInfo(name = "Contrasena")
    val password: String
)
