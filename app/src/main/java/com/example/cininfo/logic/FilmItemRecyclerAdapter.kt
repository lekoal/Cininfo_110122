package com.example.cininfo.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cininfo.R
import com.example.cininfo.data.FilmData

class FilmItemRecyclerAdapter(
    private val listFilm: List<FilmData>,
    private var onItemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<FilmItemRecyclerAdapter.FilmViewHolder>() {

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val smallFilmImage: ImageView = itemView.findViewById(R.id.smallFilmImage)
        private val filmName: TextView = itemView.findViewById(R.id.filmName)
        private val filmYear: TextView = itemView.findViewById(R.id.filmYear)

        fun bind(filmData: FilmData, onItemClickListener: OnItemClickListener?) {
            filmData.apply {
                filmName.text = name
                filmYear.text = releaseDate
                smallFilmImage.setImageResource(smallImage)
            }

            itemView.setOnClickListener {
                onItemClickListener?.onClick(filmData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.film_item, parent, false)
        return FilmViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(listFilm[position], onItemClickListener)
    }

    override fun getItemCount() = listFilm.size

    class OnItemClickListener(val itemClickListener: (filmData: FilmData) -> Unit) {
        fun onClick(filmData: FilmData) = itemClickListener(filmData)
    }
}