package com.tungtt.moviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.roger.catloadinglibrary.CatLoadingView
import com.tungtt.moviedb.ui.main.MainFragment

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

    private fun hideLoading() {
        catLoadingView.dismiss()
    }

    private fun showLoading() {
        catLoadingView.setBackgroundColor(getColor(R.color.sunset_orange))
        catLoadingView.show(supportFragmentManager, "")
    }

    val catLoadingView = CatLoadingView()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }
}