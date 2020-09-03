package com.hamzabhatti.quotes.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "quote")
@Parcelize
data class Quote(@PrimaryKey @ColumnInfo(name = "_id") val _id: String,
                 @ColumnInfo(name = "quoteText") val quoteText: String,
                 @ColumnInfo(name = "quoteAuthor") val quoteAuthor: String) : Parcelable