package com.example.cininfo.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cininfo.logic.FilmDataReceiver

class RepositoryImpl() : Repository {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getFreshFilmDataFromServer(): List<FilmDTO>?= FilmDataReceiver.getFreshList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPopularFilmDataFromServer(): List<FilmDTO>? = FilmDataReceiver.getPopularList()

    override fun getFilmDataFromLocalStorage() = FilmList

}