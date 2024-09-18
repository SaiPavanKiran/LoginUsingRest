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
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.model.TextFieldOutput
import com.rspk.internproject.model.TextFieldValues
import com.rspk.internproject.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val textFieldOutput: TextFieldOutput
):ViewModel(){
    private var otpBox1 = mutableStateOf("")
    private var otpBox2 = mutableStateOf("")
    private var otpBox3 = mutableStateOf("")
    private var otpBox4 = mutableStateOf("")
    private var otpBox5 = mutableStateOf("")
    var otpError by mutableStateOf<String?>(null)

    val list = listOf(
        TextFieldValues(value = otpBox1,{ value: String -> if(value.length <= 1) otpBox1.value = value }),
        TextFieldValues(value = otpBox2,{ value: String -> if(value.length <= 1) otpBox2.value = value }),
        TextFieldValues(value = otpBox3,{ value: String -> if(value.length <= 1) otpBox3.value = value }),
        TextFieldValues(value = otpBox4,{ value: String -> if(value.length <= 1) otpBox4.value = value }),
        TextFieldValues(value = otpBox5,{ value: String -> if(value.length <= 1) otpBox5.value = value })
    )

    fun onButtonClick():Boolean{
        val isValid = when{
            otpBox1.value.isEmpty() || otpBox2.value.isEmpty() || otpBox3.value.isEmpty() || otpBox4.value.isEmpty() || otpBox5.value.isEmpty() -> {
                otpError = "Please fill all the boxes"
                false
            }

            else -> {
                otpError = null
                true
            }
        }
        return isValid
    }

    fun resendCodeToast(response:Response<RegisterResponse>,context: Context){
        val handler = Handler(Looper.getMainLooper())
        when (response.body()?.message) {
            "OTP sent to your mobile." -> {
                handler.post {
                    Toast.makeText(context, "Otp sent to your mobile", Toast.LENGTH_SHORT).show()
                }
            }
            "Couldn't send an OTP, please try again." -> {
                handler.post {
                    Toast.makeText(context, "Error while sending Otp\nPlease try again later", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                handler.post {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun checkResponse(response:Response<RegisterResponse>,context: Context):Boolean {
        val handler = Handler(Looper.getMainLooper())
        when (response.body()?.message) {
            "OTP verified successful." -> {
                handler.post {
                    Toast.makeText(context, "Otp verified successfully", Toast.LENGTH_SHORT).show()
                }
                textFieldOutput.otpToken = response.body()?.token ?: ""
                return true
            }

            "Unable to verify OTP, please try again." -> {
                handler.post {
                    Toast.makeText(
                        context,
                        "Error while verifying Otp \nPlease try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            "Invalid OTP." -> {
                handler.post {
                    Toast.makeText(context, "Invalid Otp", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            else -> {
                handler.post {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                return false
            }

        }
    }

    suspend fun resendOtp(): Response<RegisterResponse> = withContext(Dispatchers.IO) {
        try {
            val result = loginRepository.forgotPassword(textFieldOutput.phone)

            Log.d("forgotPassword", result.body().toString())

            result
        } catch (ex: Exception) {
            Log.d("forgotPassword", ex.toString())

            Response.error(500, "Error: ${ex.message}".toResponseBody(null))
        }
    }

    suspend fun checkOtp(): Response<RegisterResponse> = withContext(Dispatchers.IO){
        try {
            val result = loginRepository.sendOtp(otpBox1.value+otpBox2.value+otpBox3.value+otpBox4.value+otpBox5.value)

            result
        }catch (ex: Exception) {
            Log.d("forgotPassword", ex.toString())

            Response.error(500, "Error: ${ex.message}".toResponseBody(null))
        }
    }
}