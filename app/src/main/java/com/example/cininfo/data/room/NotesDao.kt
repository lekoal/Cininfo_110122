package com.example.cininfo.data.room

import androidx.room.*

@Dao
interface NotesDao {
    @Query("SELECT noteText FROM NotesEntity WHERE id LIKE :id")
    fun getNoteByFilmId(id: Int?) : String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NotesEntity)

    @Update
    fun update(entity: NotesEntity)

    @Delete
    fun delete(entity: NotesEntity)
}