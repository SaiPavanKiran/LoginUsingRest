package com.rspk.internproject.compose.loginPage

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.ShortInfoText
import com.rspk.internproject.compose.customComposables.CustomTextButtons
import com.rspk.internproject.compose.customComposables.CustomTextField
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.navigation.ResetPassword
import com.rspk.internproject.viewmodels.login.OtpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OtpPage(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    otpViewModel: OtpViewModel
) {
    var buttonsEnabled by rememberSaveable { mutableStateOf(true) }
    val bottomContent = @Composable {
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextButtons(
                    onClick = {
                        buttonsEnabled = false
                        coroutineScope.launch {
                            val response = otpViewModel.resendOtp()
                            otpViewModel.resendCodeToast(response,context)
                            buttonsEnabled = true
                        }
                    },
                    text = R.string.resend_code
                )
            }
        }else{
            CustomTextButtons(
                onClick = {
                    buttonsEnabled = false
                    coroutineScope.launch {
                        val response = otpViewModel.resendOtp()
                        otpViewModel.resendCodeToast(response,context)
                        buttonsEnabled = true
                    }
                },
                text = R.string.resend_code
            )
        }
    }

    val content = @Composable {
        LazyVerticalGrid(columns = GridCells.Fixed(5)) {
            items(otpViewModel.list) {
                CustomTextField(
                    value = it.value.value,
                    onValueChange = it.onValueChange,
                    modifier = Modifier.padding(5.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        }

        if(otpViewModel.otpError != null){
            ShortInfoText(
                text = otpViewModel.otpError!!,
                textColor = colorResource(id = R.color.red),
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }

    val buttonClick = {
        if(otpViewModel.onButtonClick()){
            buttonsEnabled = false
            coroutineScope.launch {
                val response = otpViewModel.checkOtp()
                if (otpViewModel.checkResponse(response, context)) {
                    navController.navigate(ResetPassword)
                    buttonsEnabled = true
                } else {
                    buttonsEnabled = true
                }
            }
        }
    }

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        LoginPagePortraitLayout(
            text = InputText(
                text= R.string.verify_otp,
                infoText = R.string.remember_your_password,
                infoLinkText = R.string.login,
                buttonText = R.string.submit
            ),
            content = { content() },
            bottomContent = { bottomContent() },
            onLinkButtonClick = {
                navController.navigate(Login)
            },
            buttonsEnabled = buttonsEnabled,
            onButtonClick = {
                buttonClick()
            }
        )
    }else {
        LoginPageLandScapeLayout(
            text = InputText(
                text= R.string.verify_otp,
                infoText = R.string.remember_your_password,
                infoLinkText = R.string.login,
                buttonText = R.string.submit
            ),
            content = { content() },
            bottomContent = { bottomContent() },
            onLinkButtonClick = {
                navController.navigate(Login)
            },
            buttonsEnabled = buttonsEnabled,
            onButtonClick = {
                buttonClick()
            }
        )
    }
}