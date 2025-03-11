package com.example.ferreprodigital.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ferreprodigital.data.entities.UserEntity

/**
 * UserDao define las operaciones para la entidad UserEntity.
 * Permite insertar un nuevo usuario y obtener un usuario mediante su nombre de usuario.
 */

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM Usuarios WHERE Usuario = :username")
    suspend fun getUserByUsername(username: String): UserEntity?
}
