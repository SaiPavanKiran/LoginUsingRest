package com.rspk.internproject.compose.signupPage

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.PairOfTwoNavigationButtons
import com.rspk.internproject.compose.customComposables.CustomDropDownMenu
import com.rspk.internproject.compose.customComposables.CustomTextField
import com.rspk.internproject.constants.stateList
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.SignUpPageOne
import com.rspk.internproject.navigation.SignUpPageThree
import com.rspk.internproject.navigation.SignUpPageTwo
import com.rspk.internproject.viewmodels.signup.SignUpFarmInfoViewModel
import com.rspk.internproject.viewmodels.signup.isGoogleSignedIn

@Composable
fun SignUpFarmInfo(
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    signUpFarmInfoViewModel: SignUpFarmInfoViewModel = viewModel()
){
    val list = signUpFarmInfoViewModel.farmInfoPageList
    BackHandler {
        isGoogleSignedIn = false
        navController.navigate(SignUpPageOne){
            popUpTo(SignUpPageOne) {
                inclusive = true
            }
        }
    }

    val navigationButton = @Composable {
        PairOfTwoNavigationButtons(
            imageButton = R.drawable.back,
            onImageButtonClick = {
                navController.navigate(SignUpPageOne){
                    popUpTo(SignUpPageOne) {
                        inclusive = true
                    }
                }
                isGoogleSignedIn = false
            },
            contentDescription = R.string.back_button,
            buttonState = ButtonState.ButtonWithInnerText(R.string.continue_button),
            onButtonClick = {
                if(signUpFarmInfoViewModel.onButtonClick()){
                    navController.navigate(SignUpPageThree){
                        popUpTo(SignUpPageTwo) {
                            inclusive = false
                        }
                    }
                    signUpFarmInfoViewModel.uploadData()
                }
            }
        )
    }


    val content = @Composable { modifier: Modifier ->

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
                    supportingText = it.supportingText?.value
                )
            }

            item {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    CustomDropDownMenu(
                        isExpanded = signUpFarmInfoViewModel.isExpanded,
                        onExpandedChange = { signUpFarmInfoViewModel.isExpanded = it },
                        textFieldValue = signUpFarmInfoViewModel.state.value,
                        onTextFieldValueChange = { signUpFarmInfoViewModel.state.value = it },
                        list = stateList,
                        modifier = Modifier
                            .weight(0.4f)
                    ){ menuModifier ->
                        CustomTextField(
                            value = signUpFarmInfoViewModel.state.value,
                            onValueChange = {
                                signUpFarmInfoViewModel.state.value = it
                                if(!signUpFarmInfoViewModel.isExpanded) signUpFarmInfoViewModel.isExpanded = true
                            },
                            placeHolder = R.string.state,
                            trailingIcon = R.drawable.down_arrow,
                            modifier = menuModifier,
                            supportingText = signUpFarmInfoViewModel.stateError
                        )
                    }
                    CustomTextField(
                        value = signUpFarmInfoViewModel.zipCode,
                        onValueChange = { signUpFarmInfoViewModel.zipCode = it },
                        placeHolder = R.string.zip_code,
                        modifier = Modifier
                            .weight(0.6f),
                        supportingText = signUpFarmInfoViewModel.zipCodeError
                    )
                }
            }
        }
    }



    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        SignUpPagePortraitLayout(
            headlineState = SignUpPageState.WithTitles(2, R.string.farm_info),
            navigationButton = { navigationButton() },
            content = { content(it) }
        )
    }else{
        SignUpPageLandScapeLayout(
            headlineState = SignUpPageState.WithTitles(2, R.string.farm_info),
            navigationButton = { navigationButton() },
            content = { content(it) }
        )
    }

}