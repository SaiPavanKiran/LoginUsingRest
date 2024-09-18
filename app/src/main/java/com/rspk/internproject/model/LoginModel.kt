package com.rspk.internproject.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginModel(
    val email: String,
    val password: String,
    val role: String,
    @Json(name = "device_token")
    val deviceToken: String,
    val type: String,
    @Json(name = "social_id")
    val socialId: String
)