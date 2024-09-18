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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.internproject.R
import com.rspk.internproject.constants.days
import com.rspk.internproject.datastore.IsUserSignedIn
import com.rspk.internproject.model.BusinessHours
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.model.TextFieldOutput
import com.rspk.internproject.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BusinessHoursViewModel @Inject constructor(
    private val textFieldOutput: TextFieldOutput,
    private val signUpRepository: SignUpRepository,
    private val isUserSignedIn: IsUserSignedIn
):ViewModel() {
    var currentItem by mutableStateOf("M")
    var businessHoursError by mutableStateOf<String?>(null)

    private var listOfBusinessHours:List<Pair<String,MutableList<String>>> =
        listOf(
            "M" to mutableStateListOf(),
            "T" to mutableStateListOf(),
            "W" to mutableStateListOf(),
            "Th" to mutableStateListOf(),
            "F" to mutableStateListOf(),
            "S" to mutableStateListOf(),
            "Su" to mutableStateListOf()
        )


    fun onItemClicked(text:String){
        if(listOfBusinessHours[days.indexOf(currentItem)].second.contains(text)){
            listOfBusinessHours[days.indexOf(currentItem)].second.remove(text)
        }else{
            listOfBusinessHours[days.indexOf(currentItem)].second.add(text)
        }
    }

    fun timingsBoxColor(text:String): Int =
        if(listOfBusinessHours.find { it.first == currentItem }!!.second.contains(text)){
            R.color.yellow
        }else{
            R.color.text_field_container
        }

    fun onDayClicked(text:String){
        currentItem = text
    }

    fun dayBoxColor(text:String,index:Int): Int =
        if(currentItem == text) {
            R.color.red
        }
        else{
            if(listOfBusinessHours[index].second.isEmpty()){
                R.color.transparent
            }else{
                R.color.text_field_container
            }
        }


    fun onButtonClick():Boolean{
        var isValid = true
        var hasValues = false

        listOfBusinessHours.forEach{
            if(it.second.isNotEmpty()){
                hasValues = true
            }
        }
        when{
            !hasValues -> {
                businessHoursError = "*Please Select at least a day timings"
                isValid = false
            }
            else -> businessHoursError = null
        }
        return isValid
    }

    fun uploadData(){
        textFieldOutput.
            businessHours = BusinessHours(
            listOfBusinessHours[0].second,
            listOfBusinessHours[1].second,
            listOfBusinessHours[2].second,
            listOfBusinessHours[3].second,
            listOfBusinessHours[4].second,
            listOfBusinessHours[5].second,
            listOfBusinessHours[6].second
        )
    }

    fun checkingResponse(response:Response<RegisterResponse>,context: Context):Boolean{
        val handler = Handler(Looper.getMainLooper())
        when (response.body()?.message) {
            "Registered." -> {
                handler.post {
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                }
                viewModelScope.launch(Dispatchers.IO) {
                    isUserSignedIn.changeSignInState(1)
                    isUserSignedIn.changeUserEmail(textFieldOutput.email)
                }
                return true
            }
            "Email already exists." -> {
                handler.post {
                    Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            "Registration failed." -> {
                handler.post {
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            "Server error while registering." -> {
                handler.post {
                    Toast.makeText(context, "Server error while registering", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            "Access denied! unauthorized user." -> {
                handler.post {
                    Toast.makeText(context, "Access denied! unauthorized user", Toast.LENGTH_SHORT).show()
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

    suspend fun registerAccount(): Response<RegisterResponse> = withContext(Dispatchers.IO) {
        try {
            val result = signUpRepository.postData()

            Log.d("registerAccount", result.body().toString())

            result
        } catch (ex: Exception) {
            Log.d("registerAccount", ex.toString())

            Response.error(500, "Error: ${ex.message}".toResponseBody(null))
        }
    }
}