package com.tungtt.moviedb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompanyModel(

	@Json(name = "logoPath")
	val logoPath: String? = null,

	@Json(name = "name")
	val name: String? = null,

	@Json(name = "id")
	val id: Int? = null,

	@Json(name = "originCountry")
	val originCountry: String? = null
)
