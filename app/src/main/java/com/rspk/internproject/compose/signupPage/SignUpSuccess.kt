package com.rspk.internproject.compose.signupPage

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.PageHeadingForSignUp
import com.rspk.internproject.compose.ShortInfoText
import com.rspk.internproject.compose.customComposables.CustomButton
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Profile
import com.rspk.internproject.viewmodels.signup.SignUpSuccessViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpSuccess(
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    signUpSuccessViewModel: SignUpSuccessViewModel
){
    var count by rememberSaveable { mutableIntStateOf(2) }
    val navigationButton = @Composable {
        CustomButton(
            color = R.color.red,
            state = ButtonState.ButtonWithInnerText(R.string.got_it),
            onClick = {
                signUpSuccessViewModel.onButtonClick()
                navController.navigate(Profile)
            },
        )
    }

    LaunchedEffect(key1 = count < 2) {
        if(count < 2){
            coroutineScope.launch {
                delay(1500)
                count = 2
            }
        }
    }

    BackHandler {
        count--
        if(count == 1){
            Toast.makeText(context,"Press again to exit", Toast.LENGTH_SHORT).show()
        }
        if(count <= 0){
            (context as Activity).finish()
        }
    }

    val content = @Composable { modifier: Modifier ->
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item{
                Image(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = stringResource(id = R.string.success),
                    modifier = Modifier.padding(bottom = 25.dp)
                )
            }
            item {
                PageHeadingForSignUp(text = R.string.done_text)
            }
            item {
                if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    ShortInfoText(
                        text = stringResource(id = R.string.success_info),
                        textAlignment = TextAlign.Center
                    )
                } else{
                    ShortInfoText(
                        text = stringResource(id = R.string.success_info2),
                        textAlignment = TextAlign.Center
                    )
                }
            }
        }
    }

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        SignUpPagePortraitLayout(
            headlineState = SignUpPageState.WithoutTitles,
            navigationButton = { navigationButton() },
            content = { content(it) }
        )
    }else{
        Row (
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            PageHeadingForSignUp(
                text = R.string.app_name,
                modifier = Modifier
                    .padding(100.dp)
            )
            SignUpPageLandScapeLayout(
                headlineState = SignUpPageState.WithoutTitles ,
                navigationButton = { navigationButton() },
                content = { content(it) }
            )
        }
    }
}