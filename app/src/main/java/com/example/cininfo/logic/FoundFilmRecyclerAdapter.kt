package com.example.cininfo.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.example.cininfo.R
import com.example.cininfo.data.FilmDTO

class FoundFilmRecyclerAdapter(
    private val listFilm: List<FilmDTO>?,
    private var onItemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<FoundFilmRecyclerAdapter.FoundFilmViewHolder>(){
    class FoundFilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageBaseUrl = "https://www.themoviedb.org/t/p/original"
        private val smallFilmImage: ImageView = itemView.findViewById(R.id.search_result_image)
        private val filmName: TextView = itemView.findViewById(R.id.search_title)
        private val filmYear: TextView = itemView.findViewById(R.id.search_date)
        private val filmOverview: TextView = itemView.findViewById(R.id.search_overview)

        fun bind(filmData: FilmDTO?, onItemClickListener: OnItemClickListener?) {
            filmData?.apply {
                filmName.text = title
                filmYear.text = release_date
                filmOverview.text = overview
                smallFilmImage.load("$imageBaseUrl$poster_path") {
                    precision(Precision.EXACT)
                    error(R.drawable.small_no_image_temp)
                    scale(Scale.FILL)
                }
            }
            itemView.setOnClickListener {
                onItemClickListener?.onClick(filmData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundFilmViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_item, parent, false)
        return FoundFilmViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoundFilmViewHolder, position: Int) {
        holder.bind(listFilm?.get(position), onItemClickListener)
    }

    override fun getItemCount() = listFilm?.size ?: 0

    class OnItemClickListener(val itemClickListener: (filmData: FilmDTO?) -> Unit) {
        fun onClick(filmData: FilmDTO?) = itemClickListener(filmData)
    }
}