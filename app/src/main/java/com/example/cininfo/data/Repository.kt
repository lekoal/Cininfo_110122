package com.example.cininfo.data

interface Repository {
    fun getFilmDataFromServer() : FilmList
    fun getFilmDataFromLocalStorage() : FilmList
}