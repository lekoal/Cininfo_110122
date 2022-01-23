package com.example.cininfo.data

import javax.net.ssl.HttpsURLConnection

data class ResultURL(val filmList: List<FilmDTO>?, val urlConnection: HttpsURLConnection) {

    fun getList() = filmList

    fun getConnection() = urlConnection

}
