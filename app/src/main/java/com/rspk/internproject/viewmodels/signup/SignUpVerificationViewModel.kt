package com.rspk.internproject.viewmodels.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rspk.internproject.model.TextFieldOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpVerificationViewModel @Inject constructor(
    private val textFieldOutput: TextFieldOutput
):ViewModel() {
    var pdfName by mutableStateOf("")
    var pdfNameError by mutableStateOf<String?>(null)



    fun onButtonClick():Boolean{
        var isValid = true
        when{
            pdfName.isEmpty() -> {
                pdfNameError = "*Please Upload related Document For verification"
                isValid = false
            }
            else -> pdfNameError = null
        }
        return isValid
    }

    fun uploadData(){
        textFieldOutput.registrationProof = pdfName
    }
}