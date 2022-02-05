package com.example.cininfo.data

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class FilmDTO(
    val poster_path: String?,
    val adult: Boolean?,
    val overview: String?,
    val release_date: String?,
    val genre_ids: ArrayList<Int>?,
    val id: Int?,
    val original_title: String?,
    val original_language: String?,
    val title: String?,
    val backdrop_path: String?,
    val popularity: Double?,
    val vote_count: Int?,
    val video: Boolean?,
    val vote_average: Double?
): Parcelable
