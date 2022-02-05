package com.example.cininfo.logic

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.cininfo.BuildConfig
import com.example.cininfo.data.FilmDTO
import com.example.cininfo.data.FilmOuterDTO
import com.example.cininfo.data.ResultURL
import com.example.cininfo.ui.main.DETAILS_INTENT_FILTER
import com.example.cininfo.ui.main.FRESH_FILM_LIST
import com.example.cininfo.ui.main.POPULAR_FILM_LIST
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val freshUrl =
    "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&language=ru-RU&sort_by=release_date.desc&primary_release_year=2021"
private const val popularUrl =
    "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&language=ru-RU&sort_by=popularity.desc"

class GetDataService(name: String = "DataReceiverService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    lateinit var thread: Thread

    override fun onHandleIntent(intent: Intent?) {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            val freshUri =
                URL(freshUrl)
            val popularUri =
                URL(popularUrl)
            val handler = Looper.myLooper()?.let { Handler(it) }
            thread = Thread(Runnable {
                lateinit var freshUrlConnection: HttpsURLConnection
                lateinit var popularUrlConnection: HttpsURLConnection
                try {
                    val freshResultURL: ResultURL = makeConnection(freshUri)
                    val popularResultURL: ResultURL = makeConnection(popularUri)
                    freshUrlConnection = freshResultURL.getConnection()
                    popularUrlConnection = popularResultURL.getConnection()
                    val freshList = freshResultURL.getList()
                    val popularList = popularResultURL.getList()
                    handler?.post {
                        broadcastIntent.putExtra(FRESH_FILM_LIST, freshList as ArrayList<FilmDTO>?)
                        broadcastIntent.putExtra(POPULAR_FILM_LIST, popularList as ArrayList<FilmDTO>?)
                        sendBroadcast(broadcastIntent)
                    }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    AppState.Error(Throwable("Fail connection"))
                } finally {
                    freshUrlConnection.disconnect()
                    popularUrlConnection.disconnect()
                }
            })
            thread.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            AppState.Error(Throwable("Fail URI"))
        }
        return super.onStartCommand(intent, flags, startId)
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader) = reader.lines().collect(Collectors.joining("/n"))
}