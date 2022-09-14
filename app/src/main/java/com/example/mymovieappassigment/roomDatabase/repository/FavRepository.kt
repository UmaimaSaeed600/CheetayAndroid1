package com.example.mymovieappassigment.roomDatabase.model.repository

import androidx.lifecycle.LiveData
import com.example.mymovieappassigment.roomDatabase.data.FavDao
import com.example.mymovieappassigment.roomDatabase.model.FavModel


// User Repository abstracts access to multiple data sources. However this is not the part of the Architecture Component libraries.

class FavRepository(private val favDao: FavDao) {
    val readAllData: LiveData<List<FavModel>> = favDao.readAllData()

    suspend fun addUser(user: FavModel) {
        favDao.addUser(user)
    }

    suspend fun updateUser(user: FavModel) {
        favDao.updateUser(user)
    }

    suspend fun deleteUser(user: FavModel) {
        favDao.deleteUser(user)
    }

      fun getItemById(id: Long) {
        favDao.exists(id)
    }
}