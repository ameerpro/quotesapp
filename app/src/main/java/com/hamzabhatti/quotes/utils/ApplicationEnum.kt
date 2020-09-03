package com.hamzabhatti.quotes.utils

enum class ApplicationEnum {

    QUOTE_GET_SUCCESSFUL,
    QUOTE_GET_ERROR;

    var status: Boolean = false
    lateinit var message: String

    fun ApplicationEnum(isOk: Boolean, message: String) {
        this.status = isOk
        this.message = message
    }
}