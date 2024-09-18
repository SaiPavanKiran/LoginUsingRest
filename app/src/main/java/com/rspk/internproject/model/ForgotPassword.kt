package com.rspk.internproject.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForgotPassword(
    val mobile: String
)