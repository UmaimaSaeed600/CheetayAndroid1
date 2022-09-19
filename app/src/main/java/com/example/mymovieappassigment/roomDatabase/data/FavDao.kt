package com.example.mymovieappassigment.roomDatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymovieappassigment.roomDatabase.model.FavModel

@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: FavModel)

    @Update
    suspend fun updateUser(user: FavModel)

    @Delete
    suspend fun deleteUser(user: FavModel)

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE moveId = :id)")
    fun exists(id: Long): Boolean

    @Query("SELECT * from user_table ORDER BY moveId ASC")
    fun readAllData(): LiveData<List<FavModel>>
}