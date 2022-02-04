package com.example.cininfo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NotesEntity::class), version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase(){
    abstract fun notesDao(): NotesDao
}