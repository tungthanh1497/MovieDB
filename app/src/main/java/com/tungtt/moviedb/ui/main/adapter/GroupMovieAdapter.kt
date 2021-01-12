package com.tungtt.moviedb.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tungtt.moviedb.R
import com.tungtt.moviedb.model.MovieModel
import com.tungtt.moviedb.ui.main.model.GroupMovieModel
import kotlinx.android.synthetic.main.item_group_movie.view.*

class GroupMovie(_context: Context, _callback: OnGroupMovieAdapterListener) :
    ListAdapter<GroupMovieModel, GroupMovie.ItemViewholder>(DiffCallback()) {

    private val context = _context
    private val callback = _callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_group_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupMovie.ItemViewholder, position: Int) {
        holder.bind(context, getItem(position), callback)
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context, item: GroupMovieModel, callback: OnGroupMovieAdapterListener) =
            with(itemView) {
                if (!item.listMovie.isNullOrEmpty()) {
                    val firstMovie: MovieModel? = item.listMovie[0]
                    if (firstMovie != null) {
                        nameGroupTextView.text = firstMovie.title
                        descriptionGroupTextView.text = firstMovie.overview
                        ratingGroupTextView.text =
                            "Rating: ${firstMovie.voteAverage} - ${firstMovie.popularity} views"
                        Picasso.get()
                            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/${firstMovie.posterPath}")
                            .into(bannerGroupImageView)
                        groupLayout.setOnClickListener { callback.onMovieClicked(firstMovie.id) }

                        val movieAdapter = MovieAdapter(object : OnMovieAdapterListener {
                            override fun onMovieClicked(id: Int?) {
                                callback.onMovieClicked(id)
                            }
                        })
                        rv_movie.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        rv_movie.adapter = movieAdapter
                        movieAdapter.submitList(item.listMovie.filter { it?.id != firstMovie.id }
                            .toMutableList())
                    }
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
        return oldItem.listMovie?.equals(newItem.listMovie) == true
    }
}

interface OnGroupMovieAdapterListener {
    fun onMovieClicked(id: Int?)
}