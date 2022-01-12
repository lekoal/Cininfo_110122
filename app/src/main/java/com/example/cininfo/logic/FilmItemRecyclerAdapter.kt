package com.example.cininfo.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cininfo.R
import com.example.cininfo.data.FilmData

class FilmItemRecyclerAdapter(private val listFilm: List<FilmData>) : RecyclerView.Adapter<FilmItemRecyclerAdapter.FilmViewHolder>() {

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val smallFilmImage: ImageView = itemView.findViewById(R.id.smallFilmImage)
        val filmName: TextView = itemView.findViewById(R.id.filmName)
        val filmYear: TextView = itemView.findViewById(R.id.filmYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.film_item, parent, false)
        return FilmViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.filmName.text = listFilm[position].name
        holder.filmYear.text = listFilm[position].releaseDate
        holder.smallFilmImage.setImageResource(listFilm[position].smallImage)

    }

    override fun getItemCount(): Int {
        return listFilm.size
    }


}