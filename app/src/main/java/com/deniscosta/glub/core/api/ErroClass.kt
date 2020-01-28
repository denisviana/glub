package com.deniscosta.glub.core.api

import com.squareup.moshi.Json

data class ErrorClass(
    @field:Json(name = "status_message")
    val statusMessage : String,
    @field:Json(name = "status_code")
    val statusCode : String
)