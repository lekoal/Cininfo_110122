package com.example.cininfo.data

class RepositoryImpl : Repository {
    override fun getFilmDataFromServer() = FilmList


    override fun getFilmDataFromLocalStorage() = FilmList

}