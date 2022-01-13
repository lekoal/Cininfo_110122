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

    fun getFilmDataFromLocalSource() = getDataFromLocalSource()

    fun getFilmDataFromRemoteSource() = getDataFromLocalSource()

    fun getFilmList() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmDataFromLocalStorage()))
        }.start()
    }
}