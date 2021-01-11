package com.tungtt.moviedb.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by tungtt a.k.a TungTT
 * On Mon, 11 Jan 2021 - 08:55
 */
object ServiceBuilder {

    private const val mBaseUrl = "https://api.themoviedb.org/3/"
    private const val mApiKey = "903e590a2abad3f55bbb731d3c363eda"
    private const val mLanguage = "en-US"
    private const val mPage = "1"

    private lateinit var mApiBuilder: TmdbEndpoint


    fun getAPIBuilder(): TmdbEndpoint {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("api_key", mApiKey)
                .addQueryParameter("language", mLanguage)
                .addQueryParameter("page", mPage)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build())
            .build()
        mApiBuilder = retrofit.create(TmdbEndpoint::class.java)
        return mApiBuilder
    }
}