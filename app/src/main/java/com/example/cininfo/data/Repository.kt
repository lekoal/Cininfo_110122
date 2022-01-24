package com.example.cininfo.data

interface Repository {
    fun getFreshFilmDataFromServer() : List<FilmDTO>?
    fun getPopularFilmDataFromServer() : List<FilmDTO>?
    fun getFilmDataFromLocalStorage() : FilmList
}