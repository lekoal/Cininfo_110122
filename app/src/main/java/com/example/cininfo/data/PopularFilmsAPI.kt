package com.example.cininfo.data

import com.example.cininfo.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularFilmsAPI {
    @GET("movie?api_key=${ BuildConfig.TMDB_API_KEY}")
    fun getPopularFilms(
        @Query("language") language: String,
        @Query("sort_by") sort_by: String
    ): Call<FilmOuterDTO>
}