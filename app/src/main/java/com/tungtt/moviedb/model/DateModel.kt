package com.tungtt.moviedb.model

import com.google.gson.annotations.SerializedName

data class DateModel(

	@field:SerializedName("maximum")
	val maximum: String? = null,

	@field:SerializedName("minimum")
	val minimum: String? = null
)