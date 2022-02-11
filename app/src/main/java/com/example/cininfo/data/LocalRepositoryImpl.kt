package com.example.cininfo.data

import com.example.cininfo.data.room.SearchHistoryDao
import com.example.cininfo.data.room.SearchHistoryEntity

class LocalRepositoryImpl(private val localDataSource: SearchHistoryDao) : LocalRepository {
    override fun getAllSearchHistory(): List<String> {
        return localDataSource.allDesc()
    }

    override fun saveEntity(searchItem: String) {
        localDataSource.insert(convertStringToEntity(searchItem))
    }

    private fun convertStringToEntity(searchItem: String): SearchHistoryEntity {
        return SearchHistoryEntity(0, searchItem)
    }
}