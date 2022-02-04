package com.example.cininfo.data

interface Repository {
    fun getFreshFilmDataFromServer() : List<FilmDTO>?
    fun getPopularFilmDataFromServer() : List<FilmDTO>?
    fun getFoundFilmDataFromServer(query: String?, isAdult: Boolean?) : List<FilmDTO>?
    fun getFilmDataFromLocalStorage() : FilmList
}