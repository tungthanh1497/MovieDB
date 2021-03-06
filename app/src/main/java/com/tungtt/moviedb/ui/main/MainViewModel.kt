package com.tungtt.moviedb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tungtt.moviedb.network.ServiceBuilder
import com.tungtt.moviedb.ui.main.model.GroupMovieModel
import com.tungtt.moviedb.ui.main.model.GroupMovieModel.GROUP_MOVIE_NAME.Companion.NOW_PLAYING
import com.tungtt.moviedb.ui.main.model.GroupMovieModel.GROUP_MOVIE_NAME.Companion.POPULAR
import com.tungtt.moviedb.ui.main.model.GroupMovieModel.GROUP_MOVIE_NAME.Companion.TOP_RATED
import com.tungtt.moviedb.ui.main.model.GroupMovieModel.GROUP_MOVIE_NAME.Companion.UPCOMING
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val _listGroupMovieLiveData: MutableLiveData<MutableList<GroupMovieModel>> =
        MutableLiveData()
    val listGroupMovieLiveData: LiveData<MutableList<GroupMovieModel>> = _listGroupMovieLiveData

    private val listGroupMovie: MutableList<GroupMovieModel> = mutableListOf()

    fun getData(): Disposable {
        listGroupMovie.clear()

        val observableList: MutableList<Observable<*>> = mutableListOf()
        observableList.add(
            ServiceBuilder.getAPIBuilder().getUpcoming()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        if (response.results?.isNotEmpty() == true) {
                            listGroupMovie.add(
                                GroupMovieModel(
                                    UPCOMING,
                                    response.results.toMutableList()
                                )
                            )
                        }
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getTopRated()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        if (response.results?.isNotEmpty() == true) {
                            listGroupMovie.add(
                                GroupMovieModel(
                                    TOP_RATED,
                                    response.results.toMutableList()
                                )
                            )
                        }
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getPopular()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        if (response.results?.isNotEmpty() == true) {
                            listGroupMovie.add(
                                GroupMovieModel(
                                    POPULAR,
                                    response.results.toMutableList()
                                )
                            )
                        }
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getNowPlaying()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        if (response.results?.isNotEmpty() == true) {
                            listGroupMovie.add(
                                GroupMovieModel(
                                    NOW_PLAYING,
                                    response.results.toMutableList()
                                )
                            )
                        }
                    }
                }
        )

        return Observable.mergeDelayError(observableList)
            .observeOn(AndroidSchedulers.mainThread(), true)
            .doOnComplete {
                _listGroupMovieLiveData.postValue(listGroupMovie)
            }.subscribe()
    }


}