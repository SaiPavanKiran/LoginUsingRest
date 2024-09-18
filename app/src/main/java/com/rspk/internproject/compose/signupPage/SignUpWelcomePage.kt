package com.rspk.internproject.compose.signupPage

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.LogoButtons
import com.rspk.internproject.compose.PairOfTwoNavigationButtons
import com.rspk.internproject.compose.customComposables.CustomTextField
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.navigation.Profile
import com.rspk.internproject.navigation.SignUpPageOne
import com.rspk.internproject.navigation.SignUpPageTwo
import com.rspk.internproject.viewmodels.signup.SignUpWelcomePageViewModel
import com.rspk.internproject.viewmodels.signup.isAlreadySignedIn
import com.rspk.internproject.viewmodels.signup.isGoogleSignedIn

@Composable
fun SignUpWelcomePage(
    signUpWelcomePageViewModel: SignUpWelcomePageViewModel = viewModel(),
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    callbackManager: CallbackManager = LocalFacebookCallbackManager.current
){
    signUpWelcomePageViewModel.facebookButtonClick(callbackManager,context)
    val list = signUpWelcomePageViewModel.signUpWelcomePageList
    val navigationButton = @Composable {
        PairOfTwoNavigationButtons(
            textButton = R.string.login,
            onTextClick = {
                navController.navigate(Login){
                    popUpTo(Login) {
                        inclusive = true
                    }
                }
            },
            buttonState = ButtonState.ButtonWithInnerText(R.string.continue_button),
            onButtonClick = {
                if(signUpWelcomePageViewModel.onButtonClick()){
                    navController.navigate(SignUpPageTwo){
                        popUpTo(SignUpPageOne) {
                            inclusive = false
                        }
                    }
                    signUpWelcomePageViewModel.uploadData()
                }
            }
        )
    }

    val content = @Composable { modifier:Modifier ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            items(list) {
                CustomTextField(
                    value = it.value.value,
                    onValueChange = it.onValueChange,
                    leadingIcon = it.leadingIcon,
                    placeHolder = it.placeHolder,
                    supportingText = it.supportingText?.value,
                )
            }
        }
    }

    LaunchedEffect(key1 = isGoogleSignedIn){
        if(isGoogleSignedIn){
            navController.navigate(SignUpPageTwo){
                popUpTo(SignUpPageOne) {
                    inclusive = false
                }
            }
        }
    }

    LaunchedEffect(key1 = isAlreadySignedIn){
        if(isAlreadySignedIn) {
            navController.navigate(Profile) {
                popUpTo(SignUpPageOne) {
                    inclusive = true
                }
            }
        }
    }

    val signInButton = @Composable {
        LogoButtons(
            googleButton = {
                signUpWelcomePageViewModel.googleButtonClick(context)
            },
            facebookButton = {
                LoginManager.getInstance().logInWithReadPermissions(
                    context as MainActivity,
                    listOf("public_profile", "email")
                )
            }
        )
        Text(
            text = stringResource(id = R.string.or_signup_with),
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(bottom = 25.dp)
        )
    }


    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        SignUpPagePortraitLayout(
            headlineState = SignUpPageState.WithTitles(1, R.string.welcome),
            signInButton = { signInButton() },
            navigationButton = { navigationButton() },
            content = { modifier ->
                content(modifier)
            }
        )
    }
    else
        SignUpPageLandScapeLayout(
            headlineState = SignUpPageState.WithTitles(1, R.string.welcome),
            signInButton = { signInButton() },
            navigationButton = { navigationButton() },
            content = { modifier ->
                content(modifier)
            }
        )
}