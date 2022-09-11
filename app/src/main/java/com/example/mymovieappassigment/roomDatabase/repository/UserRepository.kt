package com.example.mymovieappassigment.roomDatabase.model.repository

import androidx.lifecycle.LiveData
import com.example.mymovieappassigment.roomDatabase.data.UserDao
import com.example.mymovieappassigment.roomDatabase.model.FavModel


// User Repository abstracts access to multiple data sources. However this is not the part of the Architecture Component libraries.

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<FavModel>> = userDao.readAllData()

    suspend fun addUser(user: FavModel) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: FavModel) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: FavModel) {
        userDao.deleteUser(user)
    }

      fun getItemById(id: Long) {
        userDao.exists(id)
    }
}