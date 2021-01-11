package com.tungtt.moviedb.model.getdetail

import com.tungtt.moviedb.model.GenreModel
import com.tungtt.moviedb.model.ProductionCompanyModel
import com.tungtt.moviedb.model.ProductionCountryModel
import com.tungtt.moviedb.model.SpokenLanguageModel

data class GetDetailResponse(
	val originalLanguage: String? = null,
	val imdbId: String? = null,
	val video: Boolean? = null,
	val title: String? = null,
	val backdropPath: String? = null,
	val revenue: Int? = null,
	val genres: List<GenreModel?>? = null,
	val popularity: Double? = null,
	val productionCountries: List<ProductionCountryModel?>? = null,
	val id: Int? = null,
	val voteCount: Int? = null,
	val budget: Int? = null,
	val overview: String? = null,
	val originalTitle: String? = null,
	val runtime: Int? = null,
	val posterPath: String? = null,
	val spokenLanguages: List<SpokenLanguageModel?>? = null,
	val productionCompanies: List<ProductionCompanyModel?>? = null,
	val releaseDate: String? = null,
	val voteAverage: Double? = null,
	val belongsToCollection: Any? = null,
	val tagline: String? = null,
	val adult: Boolean? = null,
	val homepage: String? = null,
	val status: String? = null
)
