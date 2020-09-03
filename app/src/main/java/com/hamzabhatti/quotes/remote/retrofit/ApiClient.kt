package com.hamzabhatti.quotes.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.hamzabhatti.quotes.BuildConfig
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private val REQUEST_TIMEOUT = 60
    private val REQUEST_READOUT = 60
    private val REQUEST_WRITEOUT = 60
    private var sRetrofit: Retrofit? = null
    private var mRetrofit: Retrofit? = null

    fun getRetrofitClient(): Retrofit? {
        if (sRetrofit == null) {
            createAPIClient()
        }
        return sRetrofit
    }

    private fun createAPIClient() {
        //-----OkHttp 3 client and intercept for logs and header authentication
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .readTimeout(REQUEST_READOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(interceptor)
        okHttpClientBuilder.addInterceptor { chain ->


            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        val okHttpClient = okHttpClientBuilder.build()
        //----------Retrofit Client
        val apiUrl = BuildConfig.API_URL

        sRetrofit = Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

}