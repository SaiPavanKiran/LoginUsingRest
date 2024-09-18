package com.rspk.internproject.viewmodels.login

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
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
import com.rspk.internproject.datastore.IsUserSignedIn
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.repository.LoginRepository
import com.rspk.internproject.viewmodels.signup.getCredentialRequestWithAuthorizedAccounts
import com.rspk.internproject.viewmodels.signup.isAlreadySignedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val isUserSignedIn: IsUserSignedIn,
):ViewModel() {
    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)
    private set
    var password by mutableStateOf("")
    var passwordError by mutableStateOf<String?>(null)
    private set

    fun onButtonClick():Boolean{
        var isValid = true
        when{
            email.isEmpty() -> {
                emailError = "Field cannot be empty"
                isValid = false
            }
            !email.matches(emailRegex) -> {
                emailError = "Invalid email format"
                isValid = false
            }
            else -> emailError = null
        }

        when {
            password.isEmpty() -> {
                passwordError = "Field cannot be empty"
                isValid = false
            }
            else -> passwordError = null
        }


        return isValid
    }


    fun checkingResponse(response:Response<RegisterResponse>):Boolean{
        when (response.body()?.message) {
            "Login successful." -> {
                passwordError = null
                emailError = null
                viewModelScope.launch(Dispatchers.IO) {
                    isUserSignedIn.changeSignInState(2)
                    isUserSignedIn.changeUserEmail(email)
                }
                return true
            }
            "Invalid password." -> {
                passwordError = "Incorrect password"
                return false
            }
            "Server error while login." -> {
                passwordError = "Internal server Error. please try again after sometime"
                return false
            }
            "Account does not exist." -> {
                emailError = "User not found"
                return false
            }
            "Account is not verified, please contact administrator." -> {
                emailError = "User not verified please contact administrator"
                return false
            }
            null -> {
                passwordError = "Invalid Credentials"
                emailError = "Invalid email"
                return false
            }
            else -> {
                emailError = null
                passwordError = null
                return false
            }
        }
    }

    suspend fun loginCheck(): Response<RegisterResponse> = withContext(Dispatchers.IO) {
        try {
            val result = loginRepository.loginCheck(email, password)

            Log.d("loginCheck", result.body().toString())

            result
        } catch (ex: Exception) {
            Log.d("loginCheck", ex.toString())

            Response.error(500, "Error: ${ex.message}".toResponseBody(null))
        }
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
                handleResult(result)
                viewModelScope.launch(Dispatchers.IO) {
                    isUserSignedIn.changeSignInState(1)
                    isUserSignedIn.changeUserEmail(email)
                }
                isAlreadySignedIn = true
            } catch (ex: GetCredentialCancellationException) {
                handler.post {
                    Toast.makeText(context, "Sign in Cancelled", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                handler.post {
                    Toast.makeText(context, "No Authorized accounts found \n Please SignUp first", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleResult(result: GetCredentialResponse){
        val credential = result.credential
        when(credential){
            is GoogleIdTokenCredential -> {
                //get google id and match it with the user email in get response from server
                //if no email found should go to farmInfo Page with google details otherwise
                //go to profile
                //need a get request from server for this
            }
        }
    }

    fun facebookButtonClick(callbackManager: CallbackManager, context: Context) {
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
                val emailFromFaceBook = obj?.getString("email")
                val id = obj?.getString("id")
                val token = accessToken?.token
                isAlreadySignedIn = true
                Log.d("MainActivity<--------", "User Profile: Name: $name, Email: $emailFromFaceBook ID: $id token = $token")
            } catch (e: Exception) {
                Log.e("MainActivity<--------", "Error fetching user profile: ${e.message}")
            }
        }
    }
}