package com.rspk.internproject.compose

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.internproject.R
import com.rspk.internproject.compose.customComposables.CustomButton
import com.rspk.internproject.compose.customComposables.CustomTextButtons
import com.rspk.internproject.compose.customComposables.LogoButton

@Composable
fun AppHeading(
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(id = R.string.app_name),
        fontWeight = FontWeight.W400,
        fontSize = 19.sp,
        modifier = modifier
            .padding(bottom = 30.dp)
    )
}

@Composable
fun ShortInfoText(
    modifier: Modifier = Modifier,
    text :String,
    textAlignment: TextAlign? = null,
    textColor: Color = Color.Gray,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    paddingValues: PaddingValues = PaddingValues(bottom = 15.dp)
){
    Text(
        text = text,
        color = textColor,
        fontWeight = FontWeight.W400,
        fontSize = fontSize,
        textAlign = textAlignment,
        modifier = modifier
            .padding(paddingValues)
    )
}

@Composable
fun ShortInfoText(
    modifier: Modifier = Modifier,
    text: String,
    buttonText:String,
    textAlignment: TextAlign? = null,
    onButtonClick: () -> Unit
){
    Row (
        modifier = modifier
            .padding(bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            color = Color.Gray,
            fontWeight = FontWeight.W400,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = textAlignment,
        )
        TextButton(
            onClick = { onButtonClick() }
        ) {
            Text(
                text = buttonText,
                color = colorResource(id = R.color.red),
                fontWeight = FontWeight.W400,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = textAlignment,
            )
        }
    }
}

@Composable
fun PageHeadingForSignUp(
    modifier: Modifier = Modifier,
    @StringRes text : Int
){
    Text(
        text = stringResource(id = text),
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        modifier = modifier
    )
}


@Composable
fun LogoButtons(
    configuration: Configuration = LocalConfiguration.current,
    googleButton:() ->Unit = {},
    appleButton:() ->Unit = {},
    facebookButton:() ->Unit = {}
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 35.dp else 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        LogoButton(
            logo = R.drawable.google_logo_search_new_svgrepo_com,
            onClick = {
                googleButton()
            },
            contentDescription = R.string.google_logo
        )
        LogoButton(
            logo = R.drawable.applelogo,
            onClick = { appleButton() },
            contentDescription = R.string.apple_logo
        )
        LogoButton(
            logo = R.drawable.facebooklogo,
            onClick = { facebookButton() },
            contentDescription = R.string.facebook_logo
        )
    }
}

sealed class ButtonState{
    data class ButtonWithInnerText(
        @StringRes val text : Int,
    ) : ButtonState()
    data class ButtonWithImage(
        @DrawableRes val image : Int,
        @StringRes val contentDescription : Int,
    ) : ButtonState()
}

@Composable
fun PairOfTwoNavigationButtons(
    buttonState: ButtonState,
    onTextClick: () -> Unit,
    @StringRes textButton : Int,
    onButtonClick: () -> Unit,
){
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        CustomTextButtons(
            onClick = {
                onTextClick()
            },
            text = textButton,
        )
        CustomButton(
            color = R.color.red,
            state = buttonState,
            onClick = {
                onButtonClick()
            },
            widthOfButton = 0.8f
        )
    }
}

@Composable
fun PairOfTwoNavigationButtons(
    buttonState: ButtonState,
    @DrawableRes imageButton : Int,
    onImageButtonClick: () -> Unit,
    buttonsEnabled:Boolean = true,
    @StringRes contentDescription : Int,
    onButtonClick: () -> Unit,
    widthOfButton : Float = 0.8f
){
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = imageButton),
            contentDescription = stringResource(id = contentDescription),
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    if(buttonsEnabled){
                        onImageButtonClick()
                    }
                }
                .padding(10.dp)
        )
        CustomButton(
            color = R.color.red,
            state = buttonState,
            onClick = {
                onButtonClick()
            },
            enabled = buttonsEnabled,
            widthOfButton = widthOfButton
        )
    }
}
