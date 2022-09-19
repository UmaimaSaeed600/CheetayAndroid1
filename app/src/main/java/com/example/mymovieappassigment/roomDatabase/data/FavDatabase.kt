package com.example.kotlinroomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovieappassigment.roomDatabase.data.FavDao
import com.example.mymovieappassigment.roomDatabase.model.FavModel


@Database(
    entities = [FavModel::class],
    version = 1,
    exportSchema = true
)
abstract class FavDatabase : RoomDatabase() {
    abstract fun userDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavDatabase? = null

        fun getDatabase(context: Context): FavDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}