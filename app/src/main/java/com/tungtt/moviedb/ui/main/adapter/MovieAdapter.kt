package com.tungtt.moviedb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tungtt.moviedb.R
import com.tungtt.moviedb.model.MovieModel
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(_callback: OnMovieAdapterListener) :
    ListAdapter<MovieModel, MovieAdapter.MovieAdapterViewholder>(MovieAdapterDiffCallback()) {

    private val callback = _callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewholder {
        return MovieAdapterViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieAdapterViewholder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    class MovieAdapterViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MovieModel, callback: OnMovieAdapterListener) = with(itemView) {
            nameTextView.text = item.title
            ratingTextView.text = "Rating: ${item.voteAverage}"
            popularityTextView.text = "${item.popularity} views"
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/${item.posterPath}")
                .into(bannerImageView)

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