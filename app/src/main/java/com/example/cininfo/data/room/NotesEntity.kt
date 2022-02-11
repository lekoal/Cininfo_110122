package com.example.cininfo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesEntity(
    @PrimaryKey
    val id: Int?,
    val noteText: String?
)
