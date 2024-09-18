package com.rspk.internproject.compose.signupPage

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.PairOfTwoNavigationButtons
import com.rspk.internproject.compose.ShortInfoText
import com.rspk.internproject.compose.customComposables.CustomButton
import com.rspk.internproject.compose.customComposables.CustomSnackBar
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.SignUpPageFour
import com.rspk.internproject.navigation.SignUpPageThree
import com.rspk.internproject.navigation.SignUpPageTwo
import com.rspk.internproject.viewmodels.signup.SignUpVerificationViewModel

@Composable
fun SignUpVerification(
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    navController: NavController = LocalNavController.current,
    signUpVerificationViewModel: SignUpVerificationViewModel = viewModel()
){

    val filesIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            result.data?.data?.let { uri ->
                signUpVerificationViewModel.pdfName = getPdfName(context,uri)?: ""
            }
        }
    }
    val navigationButton = @Composable {
        PairOfTwoNavigationButtons(
            imageButton = R.drawable.back,
            onImageButtonClick = {
                navController.navigate(SignUpPageTwo){
                    popUpTo(SignUpPageTwo) {
                        inclusive = true
                    }
                }
            },
            contentDescription = R.string.back_button,
            buttonState = ButtonState.ButtonWithInnerText(R.string.continue_button),
            onButtonClick = {
                if(signUpVerificationViewModel.onButtonClick()){
                    navController.navigate(SignUpPageFour){
                        popUpTo(SignUpPageThree) {
                            inclusive = false
                        }
                    }
                    signUpVerificationViewModel.uploadData()
                }
            }
        )
    }

    val content = @Composable { modifier: Modifier ->
        Column(
            modifier = modifier
        ) {
            ShortInfoText(
                text = stringResource(id = R.string.verification_info)
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = stringResource(id = R.string.attach_proof),
                    fontWeight = FontWeight.W300,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding()
                )
                CustomButton(
                    color = R.color.red,
                    state = ButtonState.ButtonWithImage(image = R.drawable.camera, contentDescription = R.string.verification_info),
                    onClick = {
                        launcher.launch(filesIntent)
                    },
                    widthOfButton = 0.62f,
                )
            }
            if(signUpVerificationViewModel.pdfName.isEmpty()){
                ShortInfoText(
                    text = signUpVerificationViewModel.pdfNameError?:"",
                    textColor = colorResource(id = R.color.red),
                    fontSize = 13.sp
                )
            }
            if(signUpVerificationViewModel.pdfName.isNotEmpty()){
                CustomSnackBar(
                    text = signUpVerificationViewModel.pdfName ,
                    showSnackBar = signUpVerificationViewModel.pdfName != "",
                    closeIcon = painterResource(id = R.drawable.cross),
                    onSnackBarDismiss = {
                        signUpVerificationViewModel.pdfName = ""
                    },
                    modifier = Modifier
                        .padding(vertical = 22.dp),
                    containerColor = colorResource(id = R.color.text_field_container),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    dismissActionContentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        SignUpPagePortraitLayout(
            headlineState = SignUpPageState.WithTitles(3, R.string.verification),
            navigationButton = { navigationButton() },
            content = { content(it) }
        )
    }else{
        SignUpPageLandScapeLayout(
            headlineState = SignUpPageState.WithTitles(3, R.string.verification),
            navigationButton = { navigationButton() },
            content = { content(it) }
        )
    }
}



private fun getPdfName(context: Context,uri: Uri):String?{
    var pdfName:String? = null
    context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )?.use { cursor ->
        if(cursor.moveToFirst()){
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            pdfName = cursor.getString(nameIndex)
        }
    }
    return pdfName
}