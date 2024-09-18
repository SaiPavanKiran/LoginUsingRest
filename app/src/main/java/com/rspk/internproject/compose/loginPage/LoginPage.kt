package com.rspk.internproject.compose.loginPage

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.rspk.internproject.LocalFacebookCallbackManager
import com.rspk.internproject.MainActivity
import com.rspk.internproject.R
import com.rspk.internproject.compose.LogoButtons
import com.rspk.internproject.compose.ShortInfoText
import com.rspk.internproject.compose.customComposables.CustomTextField
import com.rspk.internproject.navigation.ForgotPassword
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Profile
import com.rspk.internproject.navigation.SignUpPageOne
import com.rspk.internproject.viewmodels.login.LoginPageViewModel
import com.rspk.internproject.viewmodels.signup.isAlreadySignedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    loginPageViewModel: LoginPageViewModel = viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    callbackManager: CallbackManager = LocalFacebookCallbackManager.current
){
    loginPageViewModel.facebookButtonClick(callbackManager,context)
    var buttonState by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = isAlreadySignedIn){
        if(isAlreadySignedIn) {
            navController.navigate(Profile) {
                popUpTo(SignUpPageOne) {
                    inclusive = true
                }
            }
        }
    }
    val content = @Composable {
        CustomTextField(
            value = loginPageViewModel.email,
            onValueChange = { loginPageViewModel.email =it } ,
            placeHolder = R.string.email,
            leadingIcon = R.drawable.at_the_rate,
            supportingText = loginPageViewModel.emailError
        )
        CustomTextField(
            value = loginPageViewModel.password,
            onValueChange = { loginPageViewModel.password = it } ,
            placeHolder = R.string.password,
            leadingIcon = R.drawable.lock,
            supportingText = loginPageViewModel.passwordError,
            trailingLambda = {
                ShortInfoText(
                    text = stringResource(id = R.string.forgot),
                    textColor = colorResource(id = R.color.red),
                    paddingValues = PaddingValues(end = 10.dp),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            navController.navigate(ForgotPassword)
                        }
                )
            },
        )
    }

    val bottomContent = @Composable {
        Text(
            text = stringResource(id = R.string.or_login_with),
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(bottom = 25.dp)
        )
        LogoButtons(
            googleButton = {
                loginPageViewModel.googleButtonClick(context)
            },
            facebookButton = {
                LoginManager.getInstance().logInWithReadPermissions(
                    context as MainActivity,
                    listOf("public_profile", "email")
                )
            }
        )
    }

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        LoginPagePortraitLayout(
            text = InputText(
                text= R.string.welcome_back,
                infoText = R.string.new_here,
                infoLinkText = R.string.create_account,
                buttonText = R.string.login
            ),
            content = { content() },
            bottomContent = { bottomContent() },
            onLinkButtonClick = {
                navController.navigate(SignUpPageOne)
            },
            buttonsEnabled = buttonState,
            onButtonClick = {
                if(loginPageViewModel.onButtonClick()){
                    buttonState = false
                    coroutineScope.launch {
                        val response = loginPageViewModel.loginCheck()
                        if(loginPageViewModel.checkingResponse(response)) {
                            navController.navigate(Profile)
                            buttonState = true
                        }else{
                            buttonState = true
                        }
                    }
                }
            }
        )
    }else {
        LoginPageLandScapeLayout(
            text = InputText(
                text= R.string.welcome_back,
                infoText = R.string.new_here,
                infoLinkText = R.string.create_account,
                buttonText = R.string.login
            ),
            content = { content() },
            bottomContent = { bottomContent() },
            onLinkButtonClick = {
                navController.navigate(SignUpPageOne)
            },
            buttonsEnabled = buttonState,
            onButtonClick = {
                if(loginPageViewModel.onButtonClick()){
                    buttonState = false
                    coroutineScope.launch {
                        val response = loginPageViewModel.loginCheck()
                        if(loginPageViewModel.checkingResponse(response)) {
                            navController.navigate(Profile)
                            buttonState = true
                        }else{
                            buttonState = true
                        }
                    }
                }
            }
        )
    }
}