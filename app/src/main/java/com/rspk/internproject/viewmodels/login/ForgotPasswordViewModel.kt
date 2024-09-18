package com.rspk.internproject.viewmodels.login

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rspk.internproject.constants.countryPhoneRules
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.model.TextFieldOutput
import com.rspk.internproject.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val textFieldOutput: TextFieldOutput
):ViewModel() {
    var phoneNumber by mutableStateOf("")
    var phoneNumberError by mutableStateOf<String?>(null)
        private set

    fun onButtonClick():Boolean{
        var isValid = true
        when{
            phoneNumber.isEmpty() -> {
                phoneNumberError = "Field cannot be empty"
                isValid = false
            }
            !phoneNumber.startsWith("+") || !phoneNumber.contains("-")
                    ||phoneNumber.substringBefore("-").drop(1).toLongOrNull() == null
                    || phoneNumber.substringAfter("-").toLongOrNull() == null-> {
                phoneNumberError = "Please enter phone number in valid format i.e ex:'+91-1234567890'"
                isValid = false
            }
            !isValidCountryAndNumber(phoneNumber)-> {
                isValid = false
            }
            else -> phoneNumberError = null
        }
        return isValid
    }

    private fun isValidCountryAndNumber(phoneNumber: String): Boolean {
        val countryCode = phoneNumber.substringBefore("-")
        if (!countryPhoneRules.containsKey(countryCode)) {
            phoneNumberError = "Invalid country code"
            return false
        }
        val nationalNumberLength = phoneNumber.substringAfter("-").length
        if (nationalNumberLength != countryPhoneRules[countryCode]) {
            phoneNumberError = "Phone number must be exactly ${countryPhoneRules[countryCode]} digits for country code $countryCode"
            return false
        }
        return true
    }

    fun checkingResponse(response:Response<RegisterResponse>,context: Context):Boolean{
        val handler = Handler(Looper.getMainLooper())
        when (response.body()?.message) {
            "OTP sent to your mobile." -> {
                handler.post {
                    Toast.makeText(context, "Otp sent to your mobile", Toast.LENGTH_SHORT).show()
                }
                phoneNumberError = null
                return true
            }
            "Account with this mobile number does not exist." -> {
                phoneNumberError = "Account with this mobile number does not exist."
                return false
            }
            "Couldn't send an OTP, please try again." -> {
                handler.post {
                    Toast.makeText(context, "Error while sending Otp\nPlease try again later", Toast.LENGTH_SHORT).show()
                }
                phoneNumberError = null
                return false
            }
            else -> {
                handler.post {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                phoneNumberError = null
                return false
            }
        }
    }

    suspend fun forgotPassword(): Response<RegisterResponse> = withContext(Dispatchers.IO) {
        try {
            textFieldOutput.phone = phoneNumber.substringBefore("-")+phoneNumber.substringAfter("-")
            val result = loginRepository.forgotPassword(phoneNumber.substringBefore("-")+phoneNumber.substringAfter("-"))

            Log.d("forgotPassword", result.body().toString())

            result
        } catch (ex: Exception) {
            Log.d("forgotPassword", ex.toString())

            Response.error(500, "Error: ${ex.message}".toResponseBody(null))
        }
    }

}