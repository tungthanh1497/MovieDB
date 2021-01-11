package com.tungtt.moviedb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tungtt.moviedb.network.ServiceBuilder
import com.tungtt.moviedb.ui.main.MainViewModel.GROUP_MOVIE_NAME.Companion.NOW_PLAYING
import com.tungtt.moviedb.ui.main.MainViewModel.GROUP_MOVIE_NAME.Companion.POPULAR
import com.tungtt.moviedb.ui.main.MainViewModel.GROUP_MOVIE_NAME.Companion.TOP_RATED
import com.tungtt.moviedb.ui.main.MainViewModel.GROUP_MOVIE_NAME.Companion.UPCOMING
import com.tungtt.moviedb.ui.main.model.GroupMovieModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class MainViewModel : ViewModel() {

    @Retention(RetentionPolicy.SOURCE)
    private annotation class GROUP_MOVIE_NAME {
        companion object {
            var UPCOMING = "Upcoming"
            var TOP_RATED = "Top rated"
            var POPULAR = "Popular"
            var NOW_PLAYING = "Now Playing"
        }
    }

    private val _listGroupMovieLiveData: MutableLiveData<MutableList<GroupMovieModel>> =
        MutableLiveData()
    val listGroupMovieLiveData: LiveData<MutableList<GroupMovieModel>> = _listGroupMovieLiveData

    private val listGroupMovie: MutableList<GroupMovieModel> = mutableListOf()

    fun getData() {
        listGroupMovie.clear()

        val observableList: MutableList<Observable<*>> = mutableListOf()
        observableList.add(
            ServiceBuilder.getAPIBuilder().getUpcoming()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        listGroupMovie.add(GroupMovieModel(UPCOMING, response.results))
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getTopRated()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        listGroupMovie.add(GroupMovieModel(TOP_RATED, response.results))
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getPopular()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        listGroupMovie.add(GroupMovieModel(POPULAR, response.results))
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getNowPlaying()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        listGroupMovie.add(GroupMovieModel(NOW_PLAYING, response.results))
                    }
                }
        )

        Observable.mergeDelayError(observableList)
            .observeOn(AndroidSchedulers.mainThread(), true)
            .doOnComplete {
                _listGroupMovieLiveData.postValue(listGroupMovie)
            }.subscribe()
    }


}