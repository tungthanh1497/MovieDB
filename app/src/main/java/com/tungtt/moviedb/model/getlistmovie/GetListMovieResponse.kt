package com.tungtt.moviedb.model.getlistmovie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tungtt.moviedb.model.DateModel
import com.tungtt.moviedb.model.MovieModel

@JsonClass(generateAdapter = true)
data class GetListMovieResponse(

    @Json(name = "dates")
    val dates: DateModel? = null,

    @Json(name = "page")
    val page: Int? = null,

    @Json(name = "total_pages")
    val totalPages: Int? = null,

    @Json(name = "results")
    val results: List<MovieModel?>? = null,

    @Json(name = "total_results")
    val totalResults: Int? = null
)