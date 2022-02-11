package com.example.cininfo.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cininfo.data.Repository
import com.example.cininfo.data.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

//    fun getFilmDataFromLocalSource() = getDataFromRemoteSource()

    fun getFilmDataFromRemoteSource() = getDataFromRemoteSource()

    fun getFoundFilmDataFromRemoteSource(query: String?, isAdult: Boolean?) =
        getFoundDataFromRemoteSource(query, isAdult)

//    private fun getDataFromLocalSource() {
//        liveDataToObserve.value = AppState.Loading
//        Thread {
//            sleep(1000)
//            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmDataFromLocalStorage()))
//        }.start()
//    }

    private fun getDataFromRemoteSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    repositoryImpl.getFreshFilmDataFromServer(),
                    repositoryImpl.getPopularFilmDataFromServer()
                )
            )
        }.start()
    }

    private fun getFoundDataFromRemoteSource(query: String?, isAdult: Boolean?) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                AppState.SearchSuccess(
                    repositoryImpl.getFoundFilmDataFromServer(
                        query,
                        isAdult
                    )
                )
            )
        }.start()
    }
}