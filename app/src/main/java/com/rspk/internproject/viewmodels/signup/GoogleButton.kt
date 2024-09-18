package com.rspk.internproject.viewmodels.signup


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import java.util.Random

private const val WEB_CLIENT_ID = "688644947462-olo8nijvq6c2oucud9ctdru6944fa5u3.apps.googleusercontent.com"
var isGoogleSignedIn by mutableStateOf(false)
var isAlreadySignedIn by mutableStateOf(false)
private fun getGoogleIdOption(filterByAuthorizedAccounts:Boolean): GetGoogleIdOption{
    return GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
        .setServerClientId(WEB_CLIENT_ID)
        .setAutoSelectEnabled(true)
        .setNonce(generateNonce())
        .build()
}

fun getCredentialRequestWithAuthorizedAccounts(): GetCredentialRequest {
    return GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption(true))
        .build()
}

fun getCredentialRequestWithoutAuthorizedAccounts(): GetCredentialRequest {
    return GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption(false))
        .build()
}

private fun generateNonce(length: Int = 32): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = Random()
    return (1..length)
        .map { chars[random.nextInt(chars.length)] }
        .joinToString("")
}

