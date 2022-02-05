package com.example.cininfo.data.room

import androidx.room.*

@Dao
interface WatchInfoDao {
    @Query("SELECT * FROM WatchInfoEntity")
    fun all(): List<WatchInfoEntity>

    @Query("SELECT * FROM WatchInfoEntity WHERE id LIKE :id")
    fun getDateById(id: Int?): List<WatchInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WatchInfoEntity)

    @Update
    fun update(entity: WatchInfoEntity)

    @Delete
    fun delete(entity: WatchInfoEntity)
}