package com.example.mymovieappassigment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieappassigment.roomDatabase.model.FavModel

@Suppress("unchecked_cast")
class DetailFragmentViewModelFactory(val movies: FavModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java)) {
            return DetailFragmentViewModel(movies) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}