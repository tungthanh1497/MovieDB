package com.tungtt.moviedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tungtt.moviedb.MainActivity
import com.tungtt.moviedb.R
import com.tungtt.moviedb.model.MovieModel
import com.tungtt.moviedb.ui.detailmovie.DetailMovieFragment
import com.tungtt.moviedb.ui.search.adapter.MovieSearchAdapter
import com.tungtt.moviedb.ui.search.adapter.OnMovieSearchAdapterListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance(listener: OnSearchFragmentListener): SearchFragment {
            val searchFragment = SearchFragment()
            searchFragment.mListener = listener
            return searchFragment
        }
    }

    private lateinit var viewModel: SearchViewModel
    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter: MovieSearchAdapter
    private lateinit var mListener: OnSearchFragmentListener;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = MovieSearchAdapter(object : OnMovieSearchAdapterListener {
            override fun onMovieClicked(id: Int?) {
                val bundle = Bundle()
                id?.let { bundle.putInt(DetailMovieFragment.BUNDLE_KEY.MOVIE_ID, it) }
                mListener.onMovieClicked(id)
                MainActivity.replaceFragment(
                    activity as MainActivity,
                    DetailMovieFragment.newInstance(bundle)
                )
            }
        })
        searchRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        searchRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun onSearchSuccess(data: List<MovieModel?>?) {
        if (!data.isNullOrEmpty()) {
            adapter.submitList(data)
            searchRecyclerView.visibility = View.VISIBLE
            notMatchTextView.visibility = View.GONE
        } else {
            searchRecyclerView.visibility = View.GONE
            notMatchTextView.visibility = View.VISIBLE
        }
    }

    interface OnSearchFragmentListener {
        fun onMovieClicked(id: Int?)
    }

}