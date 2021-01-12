package com.tungtt.moviedb.common

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

/**
 * Created by tungtt a.k.a TungTT
 * On Tue, 12 Jan 2021 - 09:14
 */
class BindingCommon {
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get().load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/${imageUrl}")
        .into(view)
}

@BindingAdapter("loadBlurImage")
fun loadBlurImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/${imageUrl}")
        .transform(BlurTransformation(view.context, 10, 1))
        .into(view)
}

@BindingAdapter("setRunTime")
fun setRunTime(view: TextView, runtime: Int?) {
    val hour = runtime?.div(60)
    val min = runtime?.rem(60)
    if (hour != null && hour > 0) {
        view.text = "${hour}h ${min}mins"
    } else {
        view.text = "${min}mins"
    }
}