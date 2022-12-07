package com.example.pythonOnAndroid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ScoreEntity::class
    ],
    version = 1
)

abstract class ScoreDatabase : RoomDatabase() {
    abstract val scoreDao: ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        fun getInstance(context: Context): ScoreDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "scores_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}