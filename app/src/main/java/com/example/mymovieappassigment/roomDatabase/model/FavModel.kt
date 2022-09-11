package com.example.mymovieappassigment.roomDatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table") // User Entity represents a table within the database.
data class FavModel(
    @PrimaryKey
    val moveId: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val rating: Float,
    val overview: String,

    ) : Parcelable