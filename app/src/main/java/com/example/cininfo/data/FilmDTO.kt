package com.example.cininfo.data

import android.os.Parcelable
import com.example.cininfo.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmDTO(
    val name: String?,
    val originalName: String?,
    val releaseDate: String?,
    val country: String?,
    val description: String?,
    val smallImage: Int = R.drawable.small_no_image_temp,
    val bigImage: Int = R.drawable.big_no_image_temp
): Parcelable
