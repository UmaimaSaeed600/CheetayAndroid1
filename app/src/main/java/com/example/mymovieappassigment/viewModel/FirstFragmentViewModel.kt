package com.example.mymovieappassigment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mymovieappassigment.network.Coroutines
import com.example.mymovieappassigment.MoviesRepository
import com.example.mymovieappassigment.network.SearchMoveListener
import com.example.mymovieappassigment.roomDatabase.model.FavModel
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.Dispatchers


class FirstFragmentViewModel : ViewModel() {

    var searchMoveListener: SearchMoveListener? = null
    var movePageNum: Int = 1
    var moveName: String? = null
    private val movies = liveData(Dispatchers.IO) {
        val popularMovie = MoviesRepository.getPopularMovies().movies
        emit(popularMovie)
    }

    fun getMovie(): LiveData<List<FavModel>> {
        return movies
    }

    fun getSearchMovie(move_name: String, page: Int) {
        this.moveName = move_name
        this.movePageNum = page
        Coroutines.main {
            try {
                val response =
                    moveName?.let { MoviesRepository.searchPopularMovies(it, movePageNum) }
                response.let {
                    searchMoveListener?.getMove(response)
                    return@main
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }

        }
    }


}