package com.example.mymovieappassigment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieappassigment.Movie

class SearchDetailFragmentViewModel (detailMovie: Movie) : ViewModel() {

        private val _movies = MutableLiveData<Movie>()
        val movies: LiveData<Movie>
            get() = _movies

        init {
            _movies.value = detailMovie
        }
    }