package com.example.cininfo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FilmsRepo {
    val popularApi: PopularFilmsAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseDiscoverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        adapter.create(PopularFilmsAPI::class.java)
    }

    val freshApi: FreshFilmsAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseDiscoverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        adapter.create(FreshFilmsAPI::class.java)
    }

    val searchApi: SearchFilmsAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseSearchUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        adapter.create(SearchFilmsAPI::class.java)
    }

}