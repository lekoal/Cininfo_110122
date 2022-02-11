package com.example.cininfo.data

import com.example.cininfo.data.room.WatchInfoEntity

interface LocalWatchesRepository {
    fun getAllWatchInfo(): List<WatchInfoEntity>
    fun getWatchInfo(id: Int?): List<WatchInfoEntity>
    fun saveWatchesEntity(id: Int?, title: String?, note: String?, date: String?, watchTime: Long?)
}