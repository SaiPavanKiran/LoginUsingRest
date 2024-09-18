package com.rspk.internproject.viewmodels.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rspk.internproject.R
import com.rspk.internproject.constants.stateList
import com.rspk.internproject.constants.stateZipCodeRanges
import com.rspk.internproject.model.TextFieldOutput
import com.rspk.internproject.model.TextFieldValues
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpFarmInfoViewModel @Inject constructor(
    private val textFieldOutput: TextFieldOutput
):ViewModel() {
    private var businessName = mutableStateOf("")
    private var businessNameError = mutableStateOf<String?>(null)
    private var informalName = mutableStateOf("")
    private var streetAddress =  mutableStateOf("")
    private var streetAddressError = mutableStateOf<String?>(null)
    private var city = mutableStateOf("")
    private var cityError = mutableStateOf<String?>(null)
    var zipCode by  mutableStateOf("")
    var zipCodeError by mutableStateOf<String?>(null)
    var state = mutableStateOf("")
    var stateError by mutableStateOf<String?>(null)
    var isExpanded by  mutableStateOf(false)

    val farmInfoPageList = listOf(
        TextFieldValues(businessName,{businessName.value = it },R.drawable.tag, R.string.business_name, supportingText = businessNameError),
        TextFieldValues(informalName,{informalName.value = it },R.drawable.smiley, R.string.informal_name),
        TextFieldValues(streetAddress,{streetAddress.value = it },R.drawable.home, R.string.street_address, supportingText = streetAddressError),
        TextFieldValues(city,{city.value = it },R.drawable.location, R.string.city , supportingText = cityError),
    )

    fun onButtonClick():Boolean{
        val fields = listOf(
            businessName to businessNameError,
            streetAddress to streetAddressError,
            city to cityError
        )
        var isValid = true
        fields.forEach { (field, error) ->
            if (field.value.isEmpty()) {
                error.value = "Field cannot be empty"
                isValid = false
            } else {
                error.value = null
            }
        }

        when{
            state.value.isEmpty() && zipCode.isNotEmpty() -> {
                zipCodeError = "Select a state first"
                isValid = false
            }
            zipCode.isEmpty() -> {
                zipCodeError = "Field cannot be empty"
                isValid = false
            }
            zipCode.toLongOrNull() == null -> {
                zipCodeError = "Invalid zip code"
                isValid = false
            }
            zipCode.length != 6 -> {
                zipCodeError = "Invalid zip code"
                isValid = false
            }
            zipCode.toLong() !in stateZipCodeRanges.getValue(state.value) -> {
                zipCodeError = "Invalid zip code"
                isValid = false
            }
            else -> zipCodeError = null
        }

        when {
            state.value.isEmpty() -> {
                stateError = "Not selected"
                isValid = false
            }
            !stateList.contains(state.value) -> {
                stateError = "Invalid state"
                isValid = false
            }
            else -> stateError = null
        }


        return isValid
    }

    fun uploadData(){
        textFieldOutput.businessName = businessName.value
        textFieldOutput.informalName = informalName.value
        textFieldOutput.address = streetAddress.value
        textFieldOutput.city = city.value
        textFieldOutput.state = state.value
        textFieldOutput.zipCode = zipCode.toIntOrNull() ?: 0
    }
}