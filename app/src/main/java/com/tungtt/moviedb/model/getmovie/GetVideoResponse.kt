package com.tungtt.moviedb.model.getmovie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tungtt.moviedb.model.VideoModel

@JsonClass(generateAdapter = true)
data class GetVideoResponse(

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "results")
    val results: List<VideoModel?>? = null
)