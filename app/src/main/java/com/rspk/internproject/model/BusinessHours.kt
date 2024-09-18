package com.rspk.internproject.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BusinessHours(
    val mon:List<String>,
    val tue:List<String>,
    val wed:List<String>,
    val thu:List<String>,
    val fri:List<String>,
    val sat:List<String>,
    val sun:List<String>
)