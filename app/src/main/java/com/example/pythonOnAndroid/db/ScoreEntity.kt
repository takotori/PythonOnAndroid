package com.example.pythonOnAndroid.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntity (
    @PrimaryKey val name: String,
    @ColumnInfo(name = "score") val score: Long
)