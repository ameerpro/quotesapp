package com.hamzabhatti.quotes.repository

import com.hamzabhatti.quotes.remote.retrofit.ApiClient
import com.hamzabhatti.quotes.remote.retrofit.RemoteApiService

class RemoteRepository {


    companion object {

        private val TAG = "RemoteRepository"
        private var ourInstance: RemoteRepository? =
                RemoteRepository()

        fun getInstance(): RemoteRepository {
            if (ourInstance == null)
                ourInstance = RemoteRepository()
            return ourInstance as RemoteRepository
        }


    }

    // Get remote api client
    fun getRetrofitService(): RemoteApiService {
        return ApiClient().getRetrofitClient()!!.create(RemoteApiService::class.java)
    }

}
