package com.rspk.internproject.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rspk.internproject.model.SendStoreData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class IsUserSignedIn(
    private val dataStore: DataStore<Preferences>
){
    private val scope = CoroutineScope(Dispatchers.IO)
    companion object{
        private val IS_USER_SIGNED_IN = intPreferencesKey("is_user_signed_in")
        private val USER_EMAIL = stringPreferencesKey("user_email")
    }

    val isUserSignedIn = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("timingStore", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            SendStoreData(
                isUserSignedIn = preferences[IS_USER_SIGNED_IN] ?: -1,
                email = preferences[USER_EMAIL] ?: "",
            )
        }


    suspend fun changeSignInState(signedIn:Int){
        scope.launch {
            dataStore.edit { preferences ->
                preferences[IS_USER_SIGNED_IN] = signedIn
            }
        }
    }

    suspend fun changeUserEmail(email:String){
        scope.launch {
            dataStore.edit { preferences ->
                preferences[USER_EMAIL] = email
            }
        }
    }

}