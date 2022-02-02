package com.example.cininfo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val searchText: String
)
