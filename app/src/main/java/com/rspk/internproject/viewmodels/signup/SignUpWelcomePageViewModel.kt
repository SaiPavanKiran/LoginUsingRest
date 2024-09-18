package com.rspk.internproject.viewmodels.signup

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.rspk.internproject.R
import com.rspk.internproject.constants.countryPhoneRules
import com.rspk.internproject.datastore.IsUserSignedIn
import com.rspk.internproject.model.TextFieldOutput
import com.rspk.internproject.model.TextFieldValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class SignUpWelcomePageViewModel @Inject constructor(
    private val textFieldOutput: TextFieldOutput,
    private val isUserSignedIn: IsUserSignedIn
):ViewModel(){

    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    private val passwordRegex = ".*[!@#\$%^&*(),.?\":{}|<>].*".toRegex()
    private var fullName = mutableStateOf("")
    private var fullNameError = mutableStateOf<String?>(null)
    private var email = mutableStateOf("")
    private var emailError = mutableStateOf<String?>(null)
    private var phoneNumber = mutableStateOf("")
    private var phoneNumberError = mutableStateOf<String?>(null)
    private var password = mutableStateOf("")
    private var passwordError = mutableStateOf<String?>(null)
    private var confirmPassword = mutableStateOf("")
    private var confirmPasswordError = mutableStateOf<String?>(null)
    private var typeOfLogin by mutableStateOf("email")

    val signUpWelcomePageList = mutableStateListOf(
        TextFieldValues(fullName,{fullName.value = it },R.drawable.person, R.string.full_name, supportingText = fullNameError),
        TextFieldValues(email,{email.value = it },R.drawable.at_the_rate, R.string.email, supportingText = emailError),
        TextFieldValues(phoneNumber,{ phoneNumber.value = it },R.drawable.phone, R.string.phone_number, supportingText = phoneNumberError),
        TextFieldValues(password,{password.value = it },R.drawable.lock, R.string.password, supportingText = passwordError),
        TextFieldValues(confirmPassword,{confirmPassword.value = it },R.drawable.lock, R.string.re_enter_password, supportingText = confirmPasswordError)
    )


    fun onButtonClick():Boolean{

        var isValid = true

        when {
            fullName.value.isEmpty() -> {
                fullNameError.value = "Field cannot be empty"
                isValid = false
            }
            fullName.value.length !in 6..20 -> {
                fullNameError.value = "Name must be between 6 and 20 characters"
                isValid = false
            }

            else -> fullNameError.value = null
        }

        when{
            email.value.isEmpty() -> {
                emailError.value = "Field cannot be empty"
                isValid = false
            }
            !email.value.matches(emailRegex) -> {
                emailError.value = "Invalid email format"
                isValid = false
            }
            else -> emailError.value = null
        }

        when{
            phoneNumber.value.isEmpty() -> {
                phoneNumberError.value = "Field cannot be empty"
                isValid = false
            }
            !phoneNumber.value.startsWith("+") || !phoneNumber.value.contains("-")
                    ||phoneNumber.value.substringBefore("-").drop(1).toLongOrNull() == null
                    || phoneNumber.value.substringAfter("-").toLongOrNull() == null-> {
                phoneNumberError.value = "Please enter phone number in valid format i.e ex:'+91-1234567890'"
                isValid = false
            }
            !isValidCountryAndNumber(phoneNumber.value)-> {
                isValid = false
            }
            else -> phoneNumberError.value = null
        }

        when {
            confirmPassword.value.isEmpty() -> {
                confirmPasswordError.value = "Field cannot be empty"
                isValid = false
            }
            confirmPassword.value != password.value -> {
                confirmPasswordError.value = "Passwords do not match"
                isValid = false
            }
            else -> confirmPasswordError.value = null
        }

        when {
            password.value.isEmpty() -> {
                passwordError.value = "Field cannot be empty"
                isValid = false
            }
            password.value.length < 8 -> {
                passwordError.value = "Password must be at least 8 characters long"
                isValid = false
            }
            !password.value.any { it.isDigit() } -> {
                passwordError.value = "Password must contain at least one digit"
                isValid = false
            }
            !password.value.any { it.isUpperCase() } -> {
                passwordError.value = "Password must contain at least one uppercase letter"
                isValid = false
            }
            !password.value.any { it.isLowerCase() } -> {
                passwordError.value = "Password must contain at least one lowercase letter"
                isValid = false
            }
            !password.value.matches(passwordRegex) -> {
                passwordError.value = "Password must contain at least one special character"
                isValid = false
            }
            else -> passwordError.value = null
        }

        return isValid
    }

    private fun isValidCountryAndNumber(phoneNumber: String): Boolean {
        val countryCode = phoneNumber.substringBefore("-")
        if (!countryPhoneRules.containsKey(countryCode)) {
            phoneNumberError.value = "Invalid country code"
            return false
        }
        val nationalNumberLength = phoneNumber.substringAfter("-").length
        if (nationalNumberLength != countryPhoneRules[countryCode]) {
            phoneNumberError.value = "Phone number must be exactly ${countryPhoneRules[countryCode]} digits for country code $countryCode"
            return false
        }
        return true
    }

    fun googleButtonClick(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val credentialManager = CredentialManager.create(context)
            val handler = Handler(Looper.getMainLooper())
            try {
                val result = credentialManager
                    .getCredential(
                        context = context,
                        request = getCredentialRequestWithAuthorizedAccounts()
                    )
                if( result.credential.type.isNotEmpty() || !result.credential.data.isEmpty()){
                    handler.post{
                        Toast.makeText(context, "This account is already registered", Toast.LENGTH_SHORT).show()
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        isUserSignedIn.changeSignInState(1)
                        isUserSignedIn.changeUserEmail(email.value)
                    }
                    isAlreadySignedIn = true
                }
                handleResult(result)
            } catch (ex: GetCredentialCancellationException) {
                handler.post {
                    Toast.makeText(context, "Sign in Cancelled", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                try {
                    val result = credentialManager
                        .getCredential(
                            context = context,
                            request = getCredentialRequestWithoutAuthorizedAccounts()
                        )
                    isGoogleSignedIn = true
                    handleResult(result)
                } catch (ex: GetCredentialCancellationException) {
                    handler.post {
                        Toast.makeText(context, "Sign in Cancelled", Toast.LENGTH_SHORT).show()
                    }
                } catch (ex: Exception) {
                    handler.post {
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun facebookButtonClick(callbackManager: CallbackManager,context: Context) {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.d("isShowingUp", "cancelled")
                    Toast.makeText(context, "Sign in Cancelled", Toast.LENGTH_SHORT).show()
                }
                override fun onError(error: FacebookException) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(result: LoginResult) {
                    checkLoginStatus()
                }
            })
    }

    fun checkLoginStatus():Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if (isLoggedIn) {
            Log.d("MainActivity<--------", "User is already logged in.")
            isGoogleSignedIn = true
            fetchUserProfile(accessToken)
        } else {
            Log.d("MainActivity<--------", "User is not logged in.")
        }
        return isLoggedIn
    }

    private fun fetchUserProfile(accessToken: AccessToken?) {
        GraphRequest.newMeRequest(
            accessToken
        ) { obj, _ ->
            try {
                val name = obj?.getString("name")
                val email = obj?.getString("email")
                val id = obj?.getString("id")
                val token = accessToken?.token

                textFieldOutput.fullName = name ?: ""
                textFieldOutput.email = email ?: ""
                textFieldOutput.socialId = token ?: ""
                textFieldOutput.type = "facebook"
                textFieldOutput.deviceToken = token ?: ""

                Log.d("MainActivity<--------", "User Profile: Name: $name, Email: $email, ID: $id")
            } catch (e: Exception) {
                Log.e("MainActivity<--------", "Error fetching user profile: ${e.message}")
            }
        }
    }


    fun uploadData(){
        textFieldOutput.fullName = fullName.value
        textFieldOutput.email = email.value
        textFieldOutput.phone = phoneNumber.value.substringBefore("-")+phoneNumber.value.substringAfter("-")
        textFieldOutput.password = password.value
        textFieldOutput.type = typeOfLogin
    }

    private fun handleResult(result: GetCredentialResponse){
        when(val credential = result.credential){
            is GoogleIdTokenCredential -> {
                textFieldOutput.fullName = credential.displayName ?: ""
                textFieldOutput.email = credential.id
                textFieldOutput.type = "google"
                textFieldOutput.phone = credential.phoneNumber ?: ""
                textFieldOutput.password = generatePassword(credential.id)
                textFieldOutput.socialId = credential.idToken
                textFieldOutput.deviceToken = credential.idToken
            }
        }
    }

    private fun generatePassword(accountName: String, length: Int = 15): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(accountName.toByteArray())
        val base64Encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes)
        val specialChars = "!@#$%^&*()-_=+[]{};:,.<>/?"
        val specialCharIndex = (hashBytes[0].toInt() and 0xFF) % specialChars.length
        val specialChar = specialChars[specialCharIndex]
        val trimmedPassword = base64Encoded.substring(0, length - 1)
        val finalPassword = trimmedPassword + specialChar

        return finalPassword
    }

}