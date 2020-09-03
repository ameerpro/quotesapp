package com.hamzabhatti.quotes.remote.retrofit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET

interface RemoteApiService {

        @GET("quotes?page=1&limit=10")
        fun getQuotes(): Single<JsonObject>

}