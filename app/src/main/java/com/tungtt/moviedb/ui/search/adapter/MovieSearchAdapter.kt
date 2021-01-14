package com.tungtt.moviedb.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tungtt.moviedb.R
import com.tungtt.moviedb.databinding.ItemSearchBinding
import com.tungtt.moviedb.model.MovieModel

class MovieSearchAdapter(_callback: OnMovieSearchAdapterListener) :
    ListAdapter<MovieModel, MovieSearchAdapter.MovieSearchAdapterViewHolder>(
        MovieSearchAdapterDiffCallback()
    ) {

    private val callback = _callback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieSearchAdapterViewHolder {
        val binding: ItemSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return MovieSearchAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSearchAdapterViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    class MovieSearchAdapterViewHolder(_binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        val binding = _binding
        fun bind(item: MovieModel, callback: OnMovieSearchAdapterListener) = with(itemView) {

            binding.movieModel = item

            setOnClickListener {
                callback.onMovieClicked(item.id)
            }
        }
    }
}

class MovieSearchAdapterDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}

interface OnMovieSearchAdapterListener {
    fun onMovieClicked(id: Int?)
}