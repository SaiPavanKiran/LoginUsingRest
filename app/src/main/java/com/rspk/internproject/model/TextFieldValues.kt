package com.rspk.internproject.model

import androidx.compose.runtime.MutableState
import com.squareup.moshi.Json

data class TextFieldOutput(
    var fullName: String ="",
    var email: String = "",
    var phone:String = "",
    var password:String = "",
    var role:String  = "Farmer",
    var businessName:String  = "",
    var informalName:String = "",
    var address:String = "",
    var city:String = "",
    var state:String = "",
    var zipCode:Int = 0,
    var registrationProof:String  = "",
    var businessHours :BusinessHours = BusinessHours(emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),emptyList()),
    var deviceToken:String = "",
    var type:String = "",
    var socialId:String = "",
    var otpToken:String = ""
)

data class TextFieldValues(
    val value: MutableState<String>,
    val onValueChange: (String) -> Unit,
    val leadingIcon: Int? = null,
    val placeHolder: Int? = null,
    val trailingIcon:Int? = null,
    val supportingText:MutableState<String?>? = null
)