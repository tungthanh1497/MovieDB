package com.tungtt.moviedb.ui.detailmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tungtt.moviedb.MainActivity
import com.tungtt.moviedb.databinding.DetailMovieFragmentBinding
import com.tungtt.moviedb.ui.detailmovie.DetailMovieFragment.BUNDLE_KEY.Companion.MOVIE_ID
import com.tungtt.moviedb.ui.main.adapter.GroupMovie
import com.tungtt.moviedb.ui.main.adapter.OnGroupMovieAdapterListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.detail_movie_fragment.*

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
    private lateinit var binding: DetailMovieFragmentBinding
    private val compositeDisposable = CompositeDisposable()
    private var movieId: Int? = -1
    private lateinit var adapter: GroupMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailMovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        implementLiveData()
        initRecyclerView()
        compositeDisposable.add(viewModel.getData(movieId))
    }

    private fun implementLiveData() {
        viewModel.movieLiveData.observe(this, Observer {
            binding.movieModel = it
        })
        viewModel.getDataLiveData.observe(this, Observer {
            MainActivity.hideLoading(activity as MainActivity)
            adapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        adapter = GroupMovie(activity!!.applicationContext, object : OnGroupMovieAdapterListener {
            override fun onMovieClicked(id: Int?) {
                MainActivity.showLoading(activity as MainActivity)
                compositeDisposable.add(viewModel.getData(id))
            }
        })
        mainRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}