package com.example.cininfo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WatchInfoEntity::class), version = 1, exportSchema = false)
abstract class WatchInfoDataBase : RoomDatabase(){
    abstract fun watchInfoDao(): WatchInfoDao
}