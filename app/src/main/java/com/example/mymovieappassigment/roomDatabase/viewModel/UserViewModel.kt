package com.example.kotlinroomdatabase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinroomdatabase.data.UserDatabase
import com.example.mymovieappassigment.roomDatabase.model.FavModel
import com.example.mymovieappassigment.roomDatabase.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// UserViewModel provides users data to the UI and survive configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<FavModel>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
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