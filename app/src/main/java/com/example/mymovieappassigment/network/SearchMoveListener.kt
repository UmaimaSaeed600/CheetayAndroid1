package com.example.mymovieappassigment.network

import com.example.mymovieappassigment.model.MoviesResponse

interface SearchMoveListener {
    fun getMove(response: MoviesResponse?)
}