package com.tungtt.moviedb.ui.detailmovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tungtt.moviedb.model.getdetail.GetDetailResponse
import com.tungtt.moviedb.network.ServiceBuilder
import com.tungtt.moviedb.ui.main.model.GroupMovieModel
import com.tungtt.moviedb.ui.main.model.GroupMovieModel.GROUP_MOVIE_NAME.Companion.RECOMMENDATION
import com.tungtt.moviedb.ui.main.model.GroupMovieModel.GROUP_MOVIE_NAME.Companion.SIMILAR
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel : ViewModel() {

    private val _movieLiveData: MutableLiveData<GetDetailResponse> = MutableLiveData()
    private val _getDataLiveData: MutableLiveData<MutableList<GroupMovieModel>> = MutableLiveData()
    val movieLiveData = _movieLiveData
    val getDataLiveData = _getDataLiveData

    private val listGroupMovie: MutableList<GroupMovieModel> = mutableListOf()

    fun getData(movieId: Int?): Disposable {

        listGroupMovie.clear()

        val observableList: MutableList<Observable<*>> = mutableListOf()
        observableList.add(
            ServiceBuilder.getAPIBuilder().getDetails(movieId.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        _movieLiveData.postValue(response)
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getSimilarMovies(movieId.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        if (response.results?.isNotEmpty() == true) {
                            listGroupMovie.add(
                                GroupMovieModel(
                                    SIMILAR,
                                    response.results.toMutableList()
                                )
                            )
                        }
                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getRecommendationsMovies(movieId.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {
                        if (response.results?.isNotEmpty() == true) {
                            listGroupMovie.add(
                                GroupMovieModel(
                                    RECOMMENDATION,
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
                _getDataLiveData.postValue(listGroupMovie)
            }.subscribe()
    }
}