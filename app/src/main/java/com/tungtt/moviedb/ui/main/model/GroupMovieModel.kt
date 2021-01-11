package com.tungtt.moviedb.ui.main.model

import com.squareup.moshi.JsonClass
import com.tungtt.moviedb.model.MovieModel

/**
 * Created by tungtt a.k.a TungTT
 * On Mon, 11 Jan 2021 - 13:40
 */
@JsonClass(generateAdapter = true)
class GroupMovieModel(
    val groupName: String,
    val listMovie: List<MovieModel?>? = null
)