package com.example.mymovieappassigment

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/3/movie/popular?api_key=e5ea3092880f4f3c25fbc537e9b37dc1")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MoviesResponse


    @GET("3/search/movie?api_key=e5ea3092880f4f3c25fbc537e9b37dc1&query")
    suspend fun searchPopularMovies(
        @Query("query") move_name: String,
        @Query("page") page: Int
    ): MoviesResponse
}