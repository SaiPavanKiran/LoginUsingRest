package com.rspk.internproject.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val token:String? = null
)


