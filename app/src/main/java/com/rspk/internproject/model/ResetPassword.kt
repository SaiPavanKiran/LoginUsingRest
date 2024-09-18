package com.rspk.internproject.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResetPassword(
    val token: String,
    val password: String,
    @Json(name = "cpassword")
    val cPassword: String
)