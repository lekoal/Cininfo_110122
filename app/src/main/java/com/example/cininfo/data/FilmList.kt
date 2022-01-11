package com.example.cininfo.data

import com.example.cininfo.R

object FilmList {

    fun getFreshFilms() = listOf(
        FilmData("Белая ворона: эпилог", "2021", "США", R.drawable.small_belaya_vorona_epilog_2021),
        FilmData("Магазинные воришки всего мира", "2021", "США", R.drawable.small_magazinnye_vorishki_vsego_mira_2021),
        FilmData("Мужские слзёзы", "2021", "США", R.drawable.small_muzhskie_slezy_2021),
        FilmData("Поступь хаоса", "2021", "США, Канада, Гонконг, Люксембург", R.drawable.small_postup_haosa_2021),
        FilmData("Шоу андроидов", "2021", "Великобритания", R.drawable.small_shou_androidov_2021),
        FilmData("Те, кто желает мне смерти", "2021", "Канада, США", R.drawable.small_te_kto_zhelaet_mne_smerti_2021),
        FilmData("Три семьи", "2021", "Италия, Франция", R.drawable.small_tri_semi_2021)
    )

    fun getPopularFilms() = listOf(
        FilmData("Форрест Гамп", "1994", "США", R.drawable.small_forrest_gump_1994)
    )

}