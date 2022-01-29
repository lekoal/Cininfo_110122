package com.example.cininfo.data

class RepositoryImpl : Repository {

    override fun getPopularFilmDataFromServer(): List<FilmDTO>? {
//        var data: List<FilmDTO>? = null
//        FilmsRepo.popularApi.getPopularFilms("ru-RU", "popularity.desc")
//            .enqueue(object : retrofit2.Callback<List<FilmDTO>> {
//                override fun onResponse(
//                    call: Call<List<FilmDTO>>,
//                    response: Response<List<FilmDTO>>
//                ) {
//                    if (response.isSuccessful) {
//                        data = response.body()
//                    }
//                }
//
//                override fun onFailure(call: Call<List<FilmDTO>>, t: Throwable) {
//                    AppState.Error(Throwable("Fail connection"))
//                }
//
//            })
//        return data
        return FilmsRepo.popularApi.getPopularFilms("ru-RU", "popularity.desc").execute().body()?.results
    }

    override fun getFreshFilmDataFromServer(): List<FilmDTO>? {
//        var data: List<FilmDTO>? = null
//        FilmsRepo.freshApi.getFreshFilms("ru-RU", "release_date.desc", "2021")
//            .enqueue(object : retrofit2.Callback<List<FilmDTO>> {
//                override fun onResponse(
//                    call: Call<List<FilmDTO>>,
//                    response: Response<List<FilmDTO>>
//                ) {
//                    if (response.isSuccessful) {
//                        data = response.body()
//                    }
//                }
//
//                override fun onFailure(call: Call<List<FilmDTO>>, t: Throwable) {
//                    AppState.Error(Throwable("Fail connection"))
//                }
//
//            })
//        return data
        return FilmsRepo.freshApi.getFreshFilms("ru-RU", "release_date.desc", "2021").execute()
            .body()?.results
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun getFreshFilmDataFromServer(): List<FilmDTO>?= FilmDataReceiver.getFreshList()


//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun getPopularFilmDataFromServer(): List<FilmDTO>? = FilmDataReceiver.getPopularList()

    override fun getFilmDataFromLocalStorage() = FilmList

}