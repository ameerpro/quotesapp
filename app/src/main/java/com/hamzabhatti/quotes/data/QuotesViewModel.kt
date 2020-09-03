package com.hamzabhatti.quotes.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hamzabhatti.quotes.data.models.ApiResponse
import com.hamzabhatti.quotes.repository.QuoteRepository
import com.hamzabhatti.quotes.data.models.Quote
import com.hamzabhatti.quotes.remote.retrofit.DisposableManager
import com.hamzabhatti.quotes.repository.RemoteRepository
import com.hamzabhatti.quotes.utils.ApiStatus
import com.hamzabhatti.quotes.utils.ApplicationEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class QuotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuoteRepository = QuoteRepository(application)
    private val allQuotes: LiveData<List<Quote>> = repository.getAllQuotes()
    private var disposable: Disposable? = null
    private var apiStatusObject: ApiStatus? = null
    private val apiStatus = MutableLiveData<ApiStatus>()

    fun saveQuote(quotes: List<Quote>) {
        repository.saveQuote(quotes)
    }

    fun getAllQuotes(): LiveData<List<Quote>> {
        return allQuotes
    }

    fun getApiStatus() : MutableLiveData<ApiStatus>{
        return apiStatus
    }

    fun getQuotesRemote(){

        disposable = RemoteRepository.getInstance().getRetrofitService().getQuotes().map { jsonObject  ->

            val apiResponse: ApiResponse = Gson().fromJson(jsonObject, ApiResponse::class.java)


            if(apiResponse.statusCode == 200) {

                apiStatusObject = ApiStatus(
                            apiResponse.message,
                            ApplicationEnum.QUOTE_GET_SUCCESSFUL,
                            jsonObject
                    )
                val quotesItems: List<Quote> = Gson().fromJson(jsonObject.get("quotes"), object : TypeToken<List<Quote>>() {}.type)
                saveQuote(quotesItems)
            }

            else{
                ApiStatus(
                        apiResponse.message,
                        ApplicationEnum.QUOTE_GET_ERROR

                )
            }
            apiStatus.postValue(apiStatusObject)
            true

        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Boolean>(){

                    override fun onSuccess(t: Boolean) {

                    }

                    override fun onError(e: Throwable) {

                    }

                })

        DisposableManager.add(disposable!!)

    }

    override fun onCleared() {
        if (disposable != null)
            DisposableManager.remove(disposable!!)
        super.onCleared()
    }
}
