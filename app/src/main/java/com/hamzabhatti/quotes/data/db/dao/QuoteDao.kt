package com.hamzabhatti.quotes.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hamzabhatti.quotes.data.models.Quote


@Dao
interface QuoteDao {

    @Insert
    suspend fun saveQuote(quote: List<Quote>)

    @Query("SELECT * FROM quote")
    fun getAllQuotes(): LiveData<List<Quote>>
}