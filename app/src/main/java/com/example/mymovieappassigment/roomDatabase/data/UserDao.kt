package com.example.mymovieappassigment.roomDatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymovieappassigment.roomDatabase.model.FavModel

// UserDao contains the methods used for accessing the database, including queries.
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    suspend fun addUser(user: FavModel)

    @Update
    suspend fun updateUser(user: FavModel)

    @Delete
    suspend fun deleteUser(user: FavModel)

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE moveId = :id)")
    fun exists(id: Long): Boolean

    @Query("SELECT * from user_table ORDER BY moveId ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
    fun readAllData(): LiveData<List<FavModel>> // <- This means function return type is List. Specifically, a List of Users.
}