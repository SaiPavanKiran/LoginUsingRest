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
import com.rspk.internproject.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val textFieldOutput: TextFieldOutput
):ViewModel() {

    private val passwordRegex = ".*[!@#\$%^&*(),.?\":{}|<>].*".toRegex()
    var newPassword by mutableStateOf("")
    var newPasswordError by mutableStateOf<String?>(null)
    var confirmPassword by mutableStateOf("")
    var confirmPasswordError by mutableStateOf<String?>(null)

    fun onButtonClick():Boolean{
        var isValid = true
        when {
            confirmPassword.isEmpty() -> {
                confirmPasswordError = "Field cannot be empty"
                isValid = false
            }
            confirmPassword != newPassword -> {
                confirmPasswordError = "Passwords do not match"
                isValid = false
            }
            else -> confirmPasswordError = null
        }

        when {
            newPassword.isEmpty() -> {
                newPasswordError = "Field cannot be empty"
                isValid = false
            }
            newPassword.length < 8 -> {
                newPasswordError= "Password must be at least 8 characters long"
                isValid = false
            }
            !newPassword.any { it.isDigit() } -> {
                newPasswordError = "Password must contain at least one digit"
                isValid = false
            }
            !newPassword.any { it.isUpperCase() } -> {
                newPasswordError = "Password must contain at least one uppercase letter"
                isValid = false
            }
            !newPassword.any { it.isLowerCase() } -> {
                newPasswordError = "Password must contain at least one lowercase letter"
                isValid = false
            }
            !newPassword.matches(passwordRegex) -> {
                newPasswordError = "Password must contain at least one special character"
                isValid = false
            }
            else -> newPasswordError = null
        }

        return isValid
    }

    fun checkingResponse(response:Response<RegisterResponse>,context: Context):Boolean{
        val handler = Handler(Looper.getMainLooper())
        when (response.body()?.message) {
            "Your password has been successfully changed." -> {
                handler.post {
                    Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            "Your password reset OTP was expired." -> {
                handler.post {
                    Toast.makeText(context, "OTP expired", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            "Invalid token." -> {
                handler.post {
                    Toast.makeText(context, "Internal token is invalid", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            "Your password reset request failed, please try again." ->{
                handler.post {
                    Toast.makeText(context, "Error while sending Otp\nPlease try again later", Toast.LENGTH_SHORT).show()
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

    suspend fun resetPassword(): Response<RegisterResponse> = withContext(Dispatchers.IO){
        try {
            val result = loginRepository.resetPassword(
                token = textFieldOutput.otpToken,
                password = newPassword,
                cPassword =confirmPassword
            )

            result
        }catch (ex: Exception) {
            Log.d("forgotPassword", ex.toString())

            Response.error(500, "Error: ${ex.message}".toResponseBody(null))
        }
    }
}