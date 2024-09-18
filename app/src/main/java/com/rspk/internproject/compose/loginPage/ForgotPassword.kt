package com.rspk.internproject.compose.loginPage

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.customComposables.CustomTextField
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.navigation.Otp
import com.rspk.internproject.viewmodels.login.ForgotPasswordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordPage(
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    forgotPasswordViewModel: ForgotPasswordViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
){
    var buttonsEnabled by rememberSaveable { mutableStateOf(true) }

    val content = @Composable {
        CustomTextField(
            value = forgotPasswordViewModel.phoneNumber,
            onValueChange = { forgotPasswordViewModel.phoneNumber = it },
            placeHolder = R.string.phone_number,
            leadingIcon = R.drawable.phone,
            supportingText = forgotPasswordViewModel.phoneNumberError
        )
    }

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        LoginPagePortraitLayout(
            text = InputText(
                text= R.string.forgot_password,
                infoText = R.string.remember_your_password,
                infoLinkText = R.string.login,
                buttonText = R.string.send_code
            ),
            content = { content() },
            buttonsEnabled = buttonsEnabled,
            bottomContent = {  },
            onLinkButtonClick = {
                navController.navigate(Login)
            },
            onButtonClick = {
                if(forgotPasswordViewModel.onButtonClick()){
                    buttonsEnabled = false
                    coroutineScope.launch {
                        val response = forgotPasswordViewModel.forgotPassword()
                        if(forgotPasswordViewModel.checkingResponse(response,context)) {
                            navController.navigate(Otp)
                            buttonsEnabled = true
                        }else{
                            buttonsEnabled = true
                        }
                    }
                }
            }
        )
    }else {
        LoginPageLandScapeLayout(
            text = InputText(
                text= R.string.forgot_password,
                infoText = R.string.remember_your_password,
                infoLinkText = R.string.login,
                buttonText = R.string.send_code
            ),
            content = { content() },
            buttonsEnabled = buttonsEnabled,
            bottomContent = {  },
            onLinkButtonClick = {
                navController.navigate(Login)
            },
            onButtonClick = {
                if(forgotPasswordViewModel.onButtonClick()){
                    buttonsEnabled = false
                    coroutineScope.launch {
                        val response = forgotPasswordViewModel.forgotPassword()
                        if(forgotPasswordViewModel.checkingResponse(response,context)) {
                            navController.navigate(Otp)
                            buttonsEnabled = true
                        }else{
                            buttonsEnabled = true
                        }
                    }
                }
            }
        )
    }
}