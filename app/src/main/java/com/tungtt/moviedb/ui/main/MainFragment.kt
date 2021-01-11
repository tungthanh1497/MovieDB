package com.tungtt.moviedb.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tungtt.moviedb.R
import com.tungtt.moviedb.network.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        tv_noti.setOnClickListener {
            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(
                ServiceBuilder.getAPIBuilder().getUpcoming()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response ->
                        run {
                            Log.d(
                                MainFragment::class.simpleName,
                                "onActivityCreated: " + response.results
                            )
                        }
                    }, { t ->
                        run {
                            Log.e(MainFragment::class.simpleName, "onActivityCreated: " + t)
                        }
                    })
            )
        }
    }

}