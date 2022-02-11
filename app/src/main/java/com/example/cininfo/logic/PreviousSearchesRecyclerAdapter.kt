package com.example.cininfo.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cininfo.R

class PreviousSearchesRecyclerAdapter(
    private val listSearches: List<String>?,
    private var onItemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<PreviousSearchesRecyclerAdapter.SearchesViewHolder>() {

    class SearchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val searchText: TextView = itemView.findViewById(R.id.previous_search_item)

        fun bind(text: String?, onItemClickListener: OnItemClickListener?) {
            searchText.text = text
            itemView.setOnClickListener {
                onItemClickListener?.onClick(text)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.previous_search_item, parent, false)
        return SearchesViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: SearchesViewHolder,
        position: Int
    ) {
        holder.bind(listSearches?.get(position), onItemClickListener)
    }

    override fun getItemCount() = listSearches?.size ?: 0

    class OnItemClickListener(val itemClickListener: (text: String?) -> Unit) {
        fun onClick(text: String?) = itemClickListener(text)
    }

}