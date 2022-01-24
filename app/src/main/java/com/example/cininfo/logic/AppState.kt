package com.example.cininfo.logic

import com.example.cininfo.data.FilmDTO

sealed class AppState {
    data class Success(val freshFilmData: List<FilmDTO>?, val popularFilmData: List<FilmDTO>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
