package com.example.cininfo.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cininfo.R
import com.example.cininfo.data.FilmDTO

class FilmItemRecyclerAdapter(
    private val listFilm: List<FilmDTO>?,
    private var onItemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<FilmItemRecyclerAdapter.FilmViewHolder>() {

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val smallFilmImage: ImageView = itemView.findViewById(R.id.smallFilmImage)
        private val filmName: TextView = itemView.findViewById(R.id.filmName)
        private val filmYear: TextView = itemView.findViewById(R.id.filmYear)

        fun bind(filmData: FilmDTO?, onItemClickListener: OnItemClickListener?) {
            filmData?.apply {
                filmName.text = title
                filmYear.text = release_date
                smallFilmImage.setImageResource(R.drawable.small_no_image_temp)
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
        holder.bind(listFilm?.get(position), onItemClickListener)
    }

    override fun getItemCount() = listFilm?.size ?: 0

    class OnItemClickListener(val itemClickListener: (filmData: FilmDTO?) -> Unit) {
        fun onClick(filmData: FilmDTO?) = itemClickListener(filmData)
    }
}