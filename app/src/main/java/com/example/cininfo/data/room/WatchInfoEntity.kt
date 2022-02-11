package com.example.cininfo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchInfoEntity(
    @PrimaryKey
    val id: Int?,
    val title: String?,
    val note: String?,
    val date: String?,
    val watchTime: Long?
)
