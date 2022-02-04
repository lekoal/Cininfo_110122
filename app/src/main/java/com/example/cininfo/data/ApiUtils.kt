package com.example.cininfo.data

object ApiUtils {
    private const val baseUrlMainPart = "https://api.themoviedb.org/"
    private const val baseUrlVersion = "3/"
    private const val discoverPart = "discover/"
    private const val searchPart = "search/"
    const val baseDiscoverUrl = "$baseUrlMainPart$baseUrlVersion$discoverPart"
    const val baseSearchUrl = "$baseUrlMainPart$baseUrlVersion$searchPart"
}