package com.tungtt.moviedb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tungtt.moviedb.R
import com.tungtt.moviedb.ui.main.adapter.GroupMovie
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter: GroupMovie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRecyclerView()
        implementLiveData()
        implementListeners()
        viewModel.getData()
    }

    private fun implementListeners() {
        mainSwipeLayout.setOnRefreshListener {
            viewModel.getData()
        }
    }

    private fun initRecyclerView() {
        adapter = GroupMovie(activity!!.applicationContext)
        mainRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.adapter = adapter
    }

    private fun implementLiveData() {
        viewModel.listGroupMovieLiveData.observe(this,
            Observer {
                mainSwipeLayout.isRefreshing = false
                if (it != null && it.size > 0) {
                    adapter.submitList(it)
                    mainRecyclerView.visibility = View.VISIBLE
                    emptyTextView.visibility = View.GONE
                }
            })
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

}