package com.example.mymovieappassigment.model

import com.example.mymovieappassigment.roomDatabase.model.FavModel
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<FavModel>,

    @SerializedName("total_pages")
    val pages: Int
)
