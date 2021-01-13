package com.tungtt.moviedb.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tungtt.moviedb.R
import com.tungtt.moviedb.databinding.ItemGroupMovieBinding
import com.tungtt.moviedb.model.MovieModel
import com.tungtt.moviedb.ui.main.model.GroupMovieModel
import kotlinx.android.synthetic.main.item_group_movie.view.*

class GroupMovie(_context: Context, _callback: OnGroupMovieAdapterListener) :
    ListAdapter<GroupMovieModel, GroupMovie.ItemViewholder>(DiffCallback()) {

    private val context = _context
    private val callback = _callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding: ItemGroupMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_group_movie,
            parent,
            false
        )
        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: GroupMovie.ItemViewholder, position: Int) {
        holder.bind(context, getItem(position), callback)
    }

    class ItemViewholder(_binding: ItemGroupMovieBinding) : RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
        fun bind(context: Context, item: GroupMovieModel, callback: OnGroupMovieAdapterListener) =
            with(itemView) {
                if (!item.listMovie.isNullOrEmpty()) {
                    val firstMovie: MovieModel? = item.listMovie[0]
                    if (firstMovie != null) {
                        binding.firstMovie = firstMovie
                        binding.handlers = callback

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