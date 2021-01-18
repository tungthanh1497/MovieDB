package com.tungtt.moviedb.common

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by Awm4n a.k.a TungTT
 * On Mon, 18 Jan 2021 - 09:28
 */
class RxSearchObservable {
    companion object {
        fun fromView(searchView: SearchView): Observable<String> {
            val subject = PublishSubject.create<String>()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String?): Boolean {
                    subject.onComplete()
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    subject.onNext(text)
                    return true
                }
            })
            return subject
        }
    }
}