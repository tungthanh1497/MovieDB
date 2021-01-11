package com.tungtt.moviedb.model.getdetail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tungtt.moviedb.model.GenreModel
import com.tungtt.moviedb.model.ProductionCompanyModel
import com.tungtt.moviedb.model.ProductionCountryModel
import com.tungtt.moviedb.model.SpokenLanguageModel

@JsonClass(generateAdapter = true)
data class GetDetailResponse(

	@Json(name = "originalLanguage")
	val originalLanguage: String? = null,

	@Json(name = "imdbId")
	val imdbId: String? = null,

	@Json(name = "video")
	val video: Boolean? = null,

	@Json(name = "title")
	val title: String? = null,

	@Json(name = "backdropPath")
	val backdropPath: String? = null,

	@Json(name = "revenue")
	val revenue: Int? = null,

	@Json(name = "genres")
	val genres: List<GenreModel?>? = null,

	@Json(name = "popularity")
	val popularity: Double? = null,

	@Json(name = "productionCountries")
	val productionCountries: List<ProductionCountryModel?>? = null,

	@Json(name = "id")
	val id: Int? = null,

	@Json(name = "voteCount")
	val voteCount: Int? = null,

	@Json(name = "budget")
	val budget: Int? = null,

	@Json(name = "overview")
	val overview: String? = null,

	@Json(name = "originalTitle")
	val originalTitle: String? = null,

	@Json(name = "runtime")
	val runtime: Int? = null,

	@Json(name = "posterPath")
	val posterPath: String? = null,

	@Json(name = "spokenLanguages")
	val spokenLanguages: List<SpokenLanguageModel?>? = null,

	@Json(name = "productionCompanies")
	val productionCompanies: List<ProductionCompanyModel?>? = null,

	@Json(name = "releaseDate")
	val releaseDate: String? = null,

	@Json(name = "voteAverage")
	val voteAverage: Double? = null,

	@Json(name = "belongsToCollection")
	val belongsToCollection: Any? = null,

	@Json(name = "tagline")
	val tagline: String? = null,

	@Json(name = "adult")
	val adult: Boolean? = null,

	@Json(name = "homepage")
	val homepage: String? = null,

	@Json(name = "status")
	val status: String? = null
)
