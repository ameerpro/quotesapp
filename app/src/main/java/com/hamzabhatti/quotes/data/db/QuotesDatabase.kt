package com.hamzabhatti.quotes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamzabhatti.quotes.data.db.dao.QuoteDao
import com.hamzabhatti.quotes.data.models.Quote


@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuotesDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        private var INSTANCE: QuotesDatabase? = null

        fun getInstance(context: Context): QuotesDatabase? {
            if (INSTANCE == null) {
                synchronized(QuotesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                            QuotesDatabase::class.java,
                            "quotes_db")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}