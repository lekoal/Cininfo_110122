package com.example.cininfo.data

import com.example.cininfo.BuildConfig

object ApiUtils {
    private val baseUrlMainPart = "https://api.themoviedb.org/"
    private val baseUrlVersion = "3/"
    private val discoverPart = "discover/"
    val baseDiscoverUrl = "$baseUrlMainPart$baseUrlVersion$discoverPart"
}