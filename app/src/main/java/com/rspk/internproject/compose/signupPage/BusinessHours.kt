package com.rspk.internproject.compose.signupPage

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.PairOfTwoNavigationButtons
import com.rspk.internproject.compose.ShortInfoText
import com.rspk.internproject.compose.customComposables.CustomActionTextBox
import com.rspk.internproject.constants.days
import com.rspk.internproject.constants.timings
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.SignUpPageFive
import com.rspk.internproject.navigation.SignUpPageThree
import com.rspk.internproject.viewmodels.signup.BusinessHoursViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BusinessHours(
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    businessHoursViewModel: BusinessHoursViewModel = viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    var buttonState by rememberSaveable { mutableStateOf(true) }
    val navigationButton = @Composable {
        PairOfTwoNavigationButtons(
            buttonState = ButtonState.ButtonWithInnerText(R.string.continue_button),
            imageButton = R.drawable.back,
            onImageButtonClick = {
                navController.navigate(SignUpPageThree){
                    popUpTo(SignUpPageThree) {
                        inclusive = true
                    }
                }
            },
            buttonsEnabled = buttonState,
            contentDescription = R.string.back_button,
            onButtonClick = {
                if(businessHoursViewModel.onButtonClick()){
                    businessHoursViewModel.uploadData()
                    buttonState = false
                    coroutineScope.launch {
                        val response = businessHoursViewModel.registerAccount()
                        if(businessHoursViewModel.checkingResponse(response,context)) {
                            navController.navigate(SignUpPageFive)
                            buttonState = true
                        }else{
                            buttonState = true
                        }
                    }
                }
            })
    }

    val content = @Composable { modifier: Modifier ->
        Column(
            modifier = modifier
        ) {
            ShortInfoText(
                text = stringResource(id = R.string.business_hours_info)
            )
            LazyVerticalGrid(columns = GridCells.Fixed(7),modifier = Modifier.padding(bottom = 7.dp)) {
                itemsIndexed(days) { index,it ->
                    CustomActionTextBox(
                        text = it,
                        boxPadding = PaddingValues(4.5.dp),
                        textPadding = PaddingValues(8.dp),
                        borderColor =
                        if(businessHoursViewModel.currentItem == it) {
                            colorResource(id = R.color.red)
                        }
                        else{
                            colorResource(id = R.color.text_field_container)
                        },
                        boxColor = colorResource(id = businessHoursViewModel.dayBoxColor(it,index)),
                        onClick = {
                            businessHoursViewModel.onDayClicked(it)
                        }
                    )
                }
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                itemsIndexed(timings) { index ,it ->
                    CustomActionTextBox(
                        text = it,
                        boxPadding = PaddingValues(5.dp),
                        textPadding = PaddingValues(vertical = 18.dp, horizontal = 10.dp),
                        boxColor = colorResource(id = businessHoursViewModel.timingsBoxColor(it)),
                        onClick = {
                            businessHoursViewModel.onItemClicked(it)
                        }
                    )
                }
            }
            if(businessHoursViewModel.businessHoursError != null){
                ShortInfoText(
                    text = businessHoursViewModel.businessHoursError!!,
                    textColor = colorResource(id = R.color.red),
                    fontSize = 13.sp
                )
            }
        }
    }


    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        SignUpPagePortraitLayout(
            headlineState = SignUpPageState.WithTitles(4, R.string.business_hours),
            navigationButton = { navigationButton() },
            content = { modifier ->
                content(modifier)
            }
        )
    }else{
        SignUpPageLandScapeLayout(
            headlineState = SignUpPageState.WithTitles(4, R.string.business_hours),
            navigationButton = { navigationButton() },
            content = { modifier ->
                content(modifier)
            }
        )
    }
}