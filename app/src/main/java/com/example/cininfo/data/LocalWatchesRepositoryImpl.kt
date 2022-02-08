package com.example.cininfo.data

import com.example.cininfo.data.room.WatchInfoDao
import com.example.cininfo.data.room.WatchInfoEntity

class LocalWatchesRepositoryImpl(private val localDataSource: WatchInfoDao) : LocalWatchesRepository{
    override fun getAllWatchInfo(): List<WatchInfoEntity> {
        return localDataSource.all()
    }

    override fun getWatchInfo(id: Int?): List<WatchInfoEntity> {
        return localDataSource.getDateById(id)
    }

    override fun getFilteredWatchInfoByTitle(title: String?): List<WatchInfoEntity> {
        return localDataSource.getDateByTitle(title)
    }

    override fun saveWatchesEntity(
        id: Int?,
        title: String?,
        note: String?,
        date: String?,
        watchTime: Long?
    ) {
        localDataSource.insert(convertDataToEntity(id, title, note, date, watchTime))
    }

    private fun convertDataToEntity(id: Int?, title: String?, note: String?, date: String?, watchTime: Long?): WatchInfoEntity {
        return WatchInfoEntity(id, title, note, date, watchTime)
    }
}