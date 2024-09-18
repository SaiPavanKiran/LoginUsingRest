package com.rspk.internproject.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FarmerEatsModel(
    @Json(name = "full_name")
    val fullName: String,
    val email: String,
    val phone:String,
    val password:String,
    val role:String,
    @Json(name = "business_name")
    val businessName:String,
    @Json(name = "informal_name")
    val informalName:String,
    val address:String,
    val city:String,
    val state:String,
    @Json(name = "zip_code")
    val zipCode:Int,
    @Json(name = "registration_proof")
    val registrationProof:String,
    @Json(name = "business_hours")
    val businessHours :BusinessHours,
    @Json(name = "device_token")
    val deviceToken:String,
    val type:String,
    @Json(name = "social_id")
    val socialId:String,
)