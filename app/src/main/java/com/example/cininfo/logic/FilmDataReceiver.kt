package com.example.cininfo.logic

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.cininfo.BuildConfig
import com.example.cininfo.data.FilmDTO
import com.example.cininfo.data.FilmOuterDTO
import com.example.cininfo.data.ResultURL
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object FilmDataReceiver {
    private const val freshUrl =
        "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&language=ru-RU&sort_by=release_date.desc&primary_release_year=2021"
    private const val popularUrl =
        "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&language=ru-RU&sort_by=popularity.desc"

    private var freshFilmList: List<FilmDTO>? = null
    private var popularFilmList: List<FilmDTO>? = null

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader) = reader.lines().collect(Collectors.joining("/n"))

    @RequiresApi(Build.VERSION_CODES.N)
    fun getFilmsServer() {

        try {
            val freshUri =
                URL(freshUrl)
            val popularUri =
                URL(popularUrl)
            val handler = Looper.myLooper()?.let { Handler(it) }
            Thread(Runnable {
                lateinit var freshUrlConnection: HttpsURLConnection
                lateinit var popularUrlConnection: HttpsURLConnection
                try {
                    val freshResultURL: ResultURL = makeConnection(freshUri)
                    val popularResultURL: ResultURL = makeConnection(popularUri)
                    freshUrlConnection = freshResultURL.getConnection()
                    popularUrlConnection = popularResultURL.getConnection()
                    val freshList = freshResultURL.getList()
                    val popularList = popularResultURL.getList()
                    handler?.post { setData(freshList, popularList) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    freshUrlConnection.disconnect()
                    popularUrlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun makeConnection(uri: URL): ResultURL {
        val urlConnection = uri.openConnection() as HttpsURLConnection
        urlConnection.requestMethod = "GET"
        urlConnection.readTimeout = 10000
        val bufferedReader =
            BufferedReader(InputStreamReader(urlConnection.inputStream))
        val filmList = Gson().fromJson(
            getLines(bufferedReader),
            FilmOuterDTO::class.java
        ).results

        return ResultURL(filmList, urlConnection)
    }

    private fun setData(freshList: List<FilmDTO>?, popularList: List<FilmDTO>?) {
        if (freshList != null) {
            freshFilmList = freshList
        }
        if (popularList != null) {
            popularFilmList = popularList
        }
    }

    fun getFreshList() = freshFilmList

    fun getPopularList() = popularFilmList
}