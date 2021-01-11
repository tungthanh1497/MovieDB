package com.tungtt.moviedb.ui.detailmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tungtt.moviedb.R
import com.tungtt.moviedb.ui.detailmovie.DetailMovieFragment.BUNDLE_KEY.Companion.MOVIE_ID

class DetailMovieFragment : Fragment() {

    @Retention(AnnotationRetention.SOURCE)
    annotation class BUNDLE_KEY {
        companion object {
            var MOVIE_ID = "movie_id"
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): DetailMovieFragment {
            val fragment = DetailMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailMovieViewModel
    private var movieId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        // TODO: Use the ViewModel
        Toast.makeText(activity, "$movieId tungtt", Toast.LENGTH_SHORT).show()
    }

}