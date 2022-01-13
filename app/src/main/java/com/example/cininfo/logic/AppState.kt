package com.example.cininfo.logic

import com.example.cininfo.data.FilmList

sealed class AppState {
    data class Success(val filmData: FilmList) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
