package com.example.mymovieappassigment

import com.example.mymovieappassigment.network.Api
import com.example.mymovieappassigment.repositories.ApiWorker
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiWorker.client)
            .build()
        api = retrofit.create(Api::class.java)
    }


    suspend fun getPopularMovies() = api.getPopularMovies(1)

    suspend fun searchPopularMovies( move_name: String, page: Int) = api.searchPopularMovies(move_name, page)

}
