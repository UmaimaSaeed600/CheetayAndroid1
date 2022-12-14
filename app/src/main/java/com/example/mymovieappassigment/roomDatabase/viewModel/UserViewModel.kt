package com.example.kotlinroomdatabase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinroomdatabase.data.FavDatabase
import com.example.mymovieappassigment.roomDatabase.model.FavModel
import com.example.mymovieappassigment.roomDatabase.model.repository.FavRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<FavModel>>
    private val repository: FavRepository

    init {
        val userDao = FavDatabase.getDatabase(application).userDao()
        repository = FavRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun getItemById(user: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getItemById(user)
        }
    }

}