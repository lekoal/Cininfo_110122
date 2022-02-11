package com.example.cininfo.data

import com.example.cininfo.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFilmsAPI {
    @GET("movie?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getFoundFilms(
        @Query("language") language: String,
        @Query("query") query: String?,
        @Query("include_adult") include_adult: Boolean?
    ): Call<FilmOuterDTO>
}