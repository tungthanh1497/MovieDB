package com.tungtt.moviedb

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.roger.catloadinglibrary.CatLoadingView
import com.tungtt.moviedb.common.RxSearchObservable
import com.tungtt.moviedb.databinding.MainActivityBinding
import com.tungtt.moviedb.model.getlistmovie.GetListMovieResponse
import com.tungtt.moviedb.network.ServiceBuilder
import com.tungtt.moviedb.ui.main.MainFragment
import com.tungtt.moviedb.ui.search.SearchFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_activity.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        fun replaceFragment(activity: MainActivity, fragment: Fragment) {
            activity.replaceFragment(fragment)
        }

        fun showLoading(activity: MainActivity) {
            activity.showLoading()
        }

        fun hideLoading(activity: MainActivity) {
            activity.hideLoading()
        }
    }

    private lateinit var binding: MainActivityBinding
    private var isShowingLoadingView = false

    private fun hideLoading() {
        if (isShowingLoadingView) {
            isShowingLoadingView = false
            catLoadingView.dismiss()
        }
    }

    private fun showLoading() {
        if (!isShowingLoadingView) {
            isShowingLoadingView = true
            catLoadingView.show(supportFragmentManager, "")
        }
    }

    val catLoadingView = CatLoadingView()
    private lateinit var searchFragment: SearchFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance())
        }

        catLoadingView.setBackgroundColor(getColor(R.color.sunset_orange))

        setupSearchView()

        binding.listener = object : OnMainActivityListener {
            override fun onSearchClicked() {
                sv_search.visibility = View.VISIBLE
                sv_search.isIconified = false
                searchFragment =
                    SearchFragment.newInstance(object : SearchFragment.OnSearchFragmentListener {
                        override fun onMovieClicked(id: Int?) {
                            sv_search.visibility = View.GONE
                        }

                    })
                replaceFragment(searchFragment)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupSearchView() {
        sv_search.queryHint = getString(R.string.hint_search_movie)

        sv_search.setOnQueryTextFocusChangeListener { v, hasFocus ->
            kotlin.run {
                if (hasFocus) {
                    sv_search.visibility = View.VISIBLE
                } else {
                    sv_search.visibility = View.GONE
                    supportFragmentManager.popBackStack()
                }
            }
        }

        RxSearchObservable.fromView(sv_search)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { t -> t.isNotEmpty() }
            .distinctUntilChanged()
            .switchMap { keyword -> ServiceBuilder.getAPIBuilder().searchMovies(keyword) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer<GetListMovieResponse> { data -> searchFragment.onSearchSuccess(data?.results) },
                Consumer<Throwable> { t ->
                    Log.e(MainActivity::class.simpleName, "setupSearchView: $t")
                })
    }

    interface OnMainActivityListener {
        fun onSearchClicked()
    }
}