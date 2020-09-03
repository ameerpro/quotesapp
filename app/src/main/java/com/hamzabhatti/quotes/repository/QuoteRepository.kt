package com.hamzabhatti.quotes.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hamzabhatti.quotes.data.db.dao.QuoteDao
import com.hamzabhatti.quotes.data.db.QuotesDatabase
import com.hamzabhatti.quotes.data.models.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class QuoteRepository(application: Application) {

    private val quotesDao: QuoteDao
    private val allQuotes: LiveData<List<Quote>>

    init {
        val database = QuotesDatabase.getInstance(application.applicationContext)
        quotesDao = database!!.quoteDao()
        allQuotes = quotesDao.getAllQuotes()
    }

    fun saveQuote(quote: List<Quote>) = runBlocking {
        this.launch(Dispatchers.IO) {
            quotesDao.saveQuote(quote)
        }
    }

    fun getAllQuotes(): LiveData<List<Quote>> {
        return allQuotes
    }
}