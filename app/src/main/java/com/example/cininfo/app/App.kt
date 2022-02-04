package com.example.cininfo.app

import android.app.Application
import androidx.room.Room
import com.example.cininfo.data.room.NotesDao
import com.example.cininfo.data.room.NotesDataBase
import com.example.cininfo.data.room.SearchHistoryDao
import com.example.cininfo.data.room.SearchHistoryDataBase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: SearchHistoryDataBase? = null
        private const val DB_NAME = "SearchHistory.db"

        private var notesDB: NotesDataBase? = null
        private const val NOTES_DB_NAME = "FilmNotes.db"

        fun getSearchHistoryDao() : SearchHistoryDao {
            if (db == null) {
                if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                db = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    SearchHistoryDataBase::class.java,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return db!!.searchHistoryDao()
        }

        fun getFilmNoteDao() : NotesDao {
            if (notesDB == null) {
                if (appInstance == null) throw java.lang.IllegalStateException("Application is null while creating DataBase")
                notesDB = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    NotesDataBase::class.java,
                    NOTES_DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return notesDB!!.notesDao()
        }
    }
}