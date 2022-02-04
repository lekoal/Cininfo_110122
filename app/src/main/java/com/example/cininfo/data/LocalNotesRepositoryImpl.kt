package com.example.cininfo.data

import com.example.cininfo.data.room.NotesDao
import com.example.cininfo.data.room.NotesEntity

class LocalNotesRepositoryImpl(private val localDataSource: NotesDao) : LocalNotesRepository {
    override fun getFilmNote(id: Int?): String? {
        return localDataSource.getNoteByFilmId(id)
    }

    override fun saveNoteEntity(id: Int?, noteText: String?) {
        localDataSource.insert(convertNoteToEntity(id, noteText))
    }

    private fun convertNoteToEntity(id: Int?, noteText: String?) : NotesEntity {
        return NotesEntity(id, noteText)
    }
}