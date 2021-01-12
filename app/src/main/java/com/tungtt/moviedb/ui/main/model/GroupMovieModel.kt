package com.tungtt.moviedb.ui.main.model

import com.squareup.moshi.JsonClass
import com.tungtt.moviedb.model.MovieModel

/**
 * Created by tungtt a.k.a TungTT
 * On Mon, 11 Jan 2021 - 13:40
 */
@JsonClass(generateAdapter = true)
class GroupMovieModel(
    @GROUP_MOVIE_NAME val groupName: String,
    val listMovie: MutableList<MovieModel?>? = null
) {

    @Retention(AnnotationRetention.SOURCE)
    annotation class GROUP_MOVIE_NAME {
        companion object {
            var UPCOMING = "Upcoming"
            var TOP_RATED = "Top rated"
            var POPULAR = "Popular"
            var NOW_PLAYING = "Now Playing"
            var SIMILAR = "Similar"
            var RECOMMENDATION = "Recommendation"
        }
    }
}