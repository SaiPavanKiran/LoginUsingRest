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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.customComposables.CustomTextField
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.viewmodels.login.ResetPasswordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordPage(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    resetPasswordViewModel: ResetPasswordViewModel = viewModel()
) {

    var buttonEnabled by rememberSaveable { mutableStateOf(true) }
    val content = @Composable {
        CustomTextField(
            value = resetPasswordViewModel.newPassword,
            onValueChange = { resetPasswordViewModel.newPassword =it } ,
            placeHolder = R.string.new_password,
            leadingIcon = R.drawable.lock,
            supportingText = resetPasswordViewModel.newPasswordError
        )
        CustomTextField(
            value = resetPasswordViewModel.confirmPassword,
            onValueChange = { resetPasswordViewModel.confirmPassword = it } ,
            placeHolder = R.string.confirm_new_password,
            leadingIcon = R.drawable.lock,
            supportingText = resetPasswordViewModel.confirmPasswordError
        )
    }

    val buttonClick = {
        if(resetPasswordViewModel.onButtonClick()){
            buttonEnabled = false
            coroutineScope.launch {
                val response = resetPasswordViewModel.resetPassword()
                if(resetPasswordViewModel.checkingResponse(response,context)) {
                    navController.navigate(Login){
                        popUpTo(Login){
                            inclusive = true
                        }
                    }
                    buttonEnabled = true
                }else{
                    buttonEnabled = true
                }
            }
        }
    }

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        LoginPagePortraitLayout(
            text = InputText(
                text= R.string.reset_password,
                infoText = R.string.remember_your_password,
                infoLinkText = R.string.login,
                buttonText = R.string.submit
            ),
            content = { content() },
            bottomContent = { },
            onLinkButtonClick = {
                navController.navigate(Login)
            },
            buttonsEnabled = buttonEnabled,
            onButtonClick = {
                buttonClick()
            }
        )
    }else {
        LoginPageLandScapeLayout(
            text = InputText(
                text= R.string.reset_password,
                infoText = R.string.remember_your_password,
                infoLinkText = R.string.login,
                buttonText = R.string.submit
            ),
            content = { content() },
            bottomContent = { },
            onLinkButtonClick = {
                navController.navigate(Login)
            },
            buttonsEnabled = buttonEnabled,
            onButtonClick = {
                buttonClick()
            }
        )
    }
}