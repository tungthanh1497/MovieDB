package com.tungtt.moviedb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguageModel(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "iso6391")
    val iso6391: String? = null,

    @Json(name = "englishName")
    val englishName: String? = null
)
