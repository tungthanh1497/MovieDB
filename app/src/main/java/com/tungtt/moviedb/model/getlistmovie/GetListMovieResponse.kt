package com.tungtt.moviedb.model.getlistmovie

import com.google.gson.annotations.SerializedName
import com.tungtt.moviedb.model.DateModel
import com.tungtt.moviedb.model.MovieModel

data class GetListMovieResponse(

    @field:SerializedName("dates")
    val dates: DateModel? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<MovieModel?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)