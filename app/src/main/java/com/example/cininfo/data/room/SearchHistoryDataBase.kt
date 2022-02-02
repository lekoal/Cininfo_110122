package com.example.cininfo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SearchHistoryEntity::class), version = 1, exportSchema = false)
abstract class SearchHistoryDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}