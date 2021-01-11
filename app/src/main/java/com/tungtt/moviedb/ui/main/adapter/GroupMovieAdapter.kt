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
import com.tungtt.moviedb.ui.main.model.GroupMovieModel
import kotlinx.android.synthetic.main.item_group_movie.view.*

class GroupMovie : ListAdapter<GroupMovieModel, GroupMovie.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_group_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupMovie.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GroupMovieModel) = with(itemView) {
            val firstMovie: MovieModel? = item.listMovie?.get(0)
            if (firstMovie != null) {
                nameTextView.text = firstMovie.title
                descriptionTextView.text = firstMovie.overview
                ratingTextView.text =
                    "Rating: ${firstMovie.voteAverage} - ${firstMovie.popularity} views"
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/${firstMovie.posterPath}")
                    .into(bannerImageView)
            }


            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<GroupMovieModel>() {

    override fun areItemsTheSame(oldItem: GroupMovieModel, newItem: GroupMovieModel): Boolean {
        return oldItem.groupName == newItem.groupName
    }

    override fun areContentsTheSame(oldItem: GroupMovieModel, newItem: GroupMovieModel): Boolean {
        return oldItem == newItem
    }
}