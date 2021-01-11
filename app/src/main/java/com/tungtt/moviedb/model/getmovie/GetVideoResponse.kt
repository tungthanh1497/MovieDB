package com.tungtt.moviedb.model.getmovie

import com.google.gson.annotations.SerializedName
import com.tungtt.moviedb.model.VideoModel

data class GetVideoResponse(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("results")
    val results: List<VideoModel?>? = null
)