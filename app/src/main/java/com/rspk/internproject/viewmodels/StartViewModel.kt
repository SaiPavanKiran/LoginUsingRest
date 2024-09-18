package com.rspk.internproject.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.internproject.datastore.IsUserSignedIn
import com.rspk.internproject.navigation.Dummy
import com.rspk.internproject.navigation.Home
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.navigation.Profile
import com.rspk.internproject.navigation.SignUpPageFive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    val isUserSignedIn: IsUserSignedIn
) :ViewModel() {

    private var isUserSigned by mutableStateOf(-2)

    init {
        viewModelScope.launch{
            isUserSignedIn.isUserSignedIn.collect {
                isUserSigned = it.isUserSignedIn
            }
        }
    }

    fun navGraphCondition() =
        when(isUserSigned){
            1 -> SignUpPageFive
            2 -> Profile
            0 -> Login
            -1 -> Home
            else -> Dummy
        }

    fun onStopCondition() {
        if(isUserSigned == 1) {
            viewModelScope.launch(Dispatchers.IO) {
                isUserSignedIn.changeSignInState(2)
            }
        }
    }
}