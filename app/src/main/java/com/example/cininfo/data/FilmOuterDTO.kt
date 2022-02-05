package com.example.cininfo.data

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class FilmOuterDTO(
    val page: Int?,
    val results: ArrayList<FilmDTO>?,
    val total_results: Int?,
    val total_pages: Int?
): Parcelable
