package com.rspk.internproject.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.login.LoginManager
import com.rspk.internproject.datastore.IsUserSignedIn
import com.rspk.internproject.viewmodels.signup.isAlreadySignedIn
import com.rspk.internproject.viewmodels.signup.isGoogleSignedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val isUserSignedIn: IsUserSignedIn
):ViewModel(){
    var email by mutableStateOf("")

    init {
        viewModelScope.launch(Dispatchers.IO){
            isUserSignedIn.isUserSignedIn.collect {
                if (it.email != "") {
                    email = it.email
                }
            }
        }
    }

    fun onExit(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            isGoogleSignedIn = false
            isAlreadySignedIn = false
            val credentialManager = CredentialManager.create(context)
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            LoginManager.getInstance().logOut()
            isUserSignedIn.changeSignInState(0)
            isUserSignedIn.changeUserEmail("")
        }
    }
}