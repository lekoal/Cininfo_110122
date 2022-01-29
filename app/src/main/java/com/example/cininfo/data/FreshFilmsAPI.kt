package com.example.cininfo.data

import com.example.cininfo.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FreshFilmsAPI {
    @GET("movie?api_key=${ BuildConfig.TMDB_API_KEY}")
    fun getFreshFilms(
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("primary_release_year") primary_release_year: String
    ): Call<FilmOuterDTO>
}