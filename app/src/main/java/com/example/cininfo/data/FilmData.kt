package com.example.cininfo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmData(
    val name: String,
    val originalName: String,
    val releaseDate: String,
    val country: String,
    val shortDescription: String,
    val longDescription: String,
    val smallImage: Int,
    val bigImage: Int
) : Parcelable
