package com.example.cininfo.data

class RepositoryImpl : Repository {
    override fun getFilmDataFromServer(): FilmList {
        return FilmList
    }

    override fun getFilmDataFromLocalStorage(): FilmList {
        return FilmList
    }

}