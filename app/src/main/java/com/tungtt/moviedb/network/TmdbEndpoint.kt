package com.tungtt.moviedb.network

import com.tungtt.moviedb.model.getdetail.GetDetailResponse
import com.tungtt.moviedb.model.getlistmovie.GetListMovieResponse
import com.tungtt.moviedb.model.getmovie.GetVideoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by tungtt a.k.a TungTT
 * On Sat, 09 Jan 2021 - 17:29
 */
interface TmdbEndpoint {
    @GET("movie/upcoming")
    fun getUpcoming(): Observable<GetListMovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(): Observable<GetListMovieResponse>

    @GET("movie/popular")
    fun getPopular(): Observable<GetListMovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(): Observable<GetListMovieResponse>

    @GET("movie/{movie_id}")
    fun getDetails(@Path("movie_id") movieId: String): Observable<GetDetailResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") movieId: String): Observable<GetVideoResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movieId: String): Observable<GetListMovieResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationsMovies(@Path("movie_id") movieId: String): Observable<GetListMovieResponse>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Observable<GetListMovieResponse>
}