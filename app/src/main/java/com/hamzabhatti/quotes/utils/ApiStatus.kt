package com.hamzabhatti.quotes.utils

import com.google.gson.JsonObject

class ApiStatus {

    internal lateinit var data: JsonObject
    internal var message: String
    internal var applicationEnum: ApplicationEnum

    constructor(message: String, applicationEnum: ApplicationEnum, data: JsonObject) {
        this.message = message
        this.data = data
        this.applicationEnum = applicationEnum
    }

    constructor(message: String, applicationEnum: ApplicationEnum) {
        this.message = message
        this.applicationEnum = applicationEnum
    }

    fun getApplicationEnum(): ApplicationEnum {
        return applicationEnum
    }

    fun jsonObject(): JsonObject {
        return data
    }


}