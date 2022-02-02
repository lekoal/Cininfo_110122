package com.example.cininfo.app

import android.app.Application
import androidx.room.Room
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
    }
}