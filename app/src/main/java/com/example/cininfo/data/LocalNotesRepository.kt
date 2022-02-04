package com.example.cininfo.data

interface LocalNotesRepository {
    fun getFilmNote(id: Int?): String?
    fun saveNoteEntity(id: Int?, noteText: String?)
}