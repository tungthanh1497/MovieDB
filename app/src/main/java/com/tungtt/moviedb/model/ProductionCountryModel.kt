package com.tungtt.moviedb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountryModel(

    @Json(name = "iso31661")
    val iso31661: String? = null,

    @Json(name = "name")
    val name: String? = null
)
