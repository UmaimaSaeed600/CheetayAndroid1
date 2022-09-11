package com.example.mymovieappassigment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieappassigment.roomDatabase.model.FavModel


class FavDetailFragmentViewModel(detailMovie: FavModel) : ViewModel() {

    private val _movies = MutableLiveData<FavModel>()
    val movies: LiveData<FavModel>
        get() = _movies

    init {
        _movies.value = detailMovie
    }
}