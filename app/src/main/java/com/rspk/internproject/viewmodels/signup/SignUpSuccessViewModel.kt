package com.rspk.internproject.viewmodels.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.internproject.datastore.IsUserSignedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpSuccessViewModel @Inject constructor(
    private val isUserSignedIn: IsUserSignedIn
):ViewModel(){

    fun onButtonClick(){
        viewModelScope.launch(Dispatchers.IO) {
            isUserSignedIn.changeSignInState(2)
        }
    }
}