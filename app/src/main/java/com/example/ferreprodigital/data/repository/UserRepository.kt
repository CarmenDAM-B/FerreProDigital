package com.example.ferreprodigital.data.repository

import com.example.ferreprodigital.data.dao.UserDao
import com.example.ferreprodigital.data.entities.UserEntity

/**
 * UserRepository actúa como capa intermedia entre el UserDao y la lógica de negocio.
 * Permite insertar un usuario y obtener un usuario por su nombre de usuario.
 */

class UserRepository(private val userDao: UserDao) {

    // Inserta un nuevo usuario en la base de datos.
    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    // Recupera un usuario de la base de datos según su nombre de usuario.
    suspend fun getUserByUsername(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }
}
