package com.tungtt.moviedb.ui.detailmovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tungtt.moviedb.model.getdetail.GetDetailResponse
import com.tungtt.moviedb.network.ServiceBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel : ViewModel() {

    private val _movieLiveData: MutableLiveData<GetDetailResponse> = MutableLiveData()
    private val _getDataLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val movieLiveData = _movieLiveData
    val getDataLiveData = _getDataLiveData

    fun getData(movieId: Int?): Disposable {
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

                    }
                }
        )
        observableList.add(
            ServiceBuilder.getAPIBuilder().getRecommendationsMovies(movieId.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    run {

                    }
                }
        )

        return Observable.mergeDelayError(observableList)
            .observeOn(AndroidSchedulers.mainThread(), true)
            .doOnComplete {
                _getDataLiveData.postValue(true)
            }.subscribe()
    }
}