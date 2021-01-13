package com.tungtt.moviedb.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tungtt.moviedb.R
import com.tungtt.moviedb.databinding.ItemMovieBinding
import com.tungtt.moviedb.model.MovieModel

class MovieAdapter(_callback: OnMovieAdapterListener) :
    ListAdapter<MovieModel, MovieAdapter.MovieAdapterViewholder>(MovieAdapterDiffCallback()) {

    private val callback = _callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewholder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return MovieAdapterViewholder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapterViewholder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    class MovieAdapterViewholder(_binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        val binding = _binding
        fun bind(item: MovieModel, callback: OnMovieAdapterListener) = with(itemView) {

            binding.movieModel = item

            setOnClickListener {
                callback.onMovieClicked(item.id)
            }
        }
    }
}

class MovieAdapterDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}

interface OnMovieAdapterListener {
    fun onMovieClicked(id: Int?)
}