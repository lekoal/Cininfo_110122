package com.example.cininfo.data.room

import androidx.room.*

@Dao
interface SearchHistoryDao {
    @Query("SELECT searchText FROM SearchHistoryEntity ORDER BY id DESC")
    fun allDesc(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: SearchHistoryEntity)

    @Update
    fun update(entity: SearchHistoryEntity)

    @Delete
    fun delete(entity: SearchHistoryEntity)
}