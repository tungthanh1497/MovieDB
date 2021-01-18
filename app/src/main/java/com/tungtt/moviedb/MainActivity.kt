package com.tungtt.moviedb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.roger.catloadinglibrary.CatLoadingView
import com.tungtt.moviedb.databinding.MainActivityBinding
import com.tungtt.moviedb.ui.main.MainFragment
import com.tungtt.moviedb.ui.search.SearchFragment
import kotlinx.android.synthetic.main.main_activity.*

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
                searchFragment = SearchFragment.newInstance()
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
    }

    interface OnMainActivityListener {
        fun onSearchClicked()
    }
}