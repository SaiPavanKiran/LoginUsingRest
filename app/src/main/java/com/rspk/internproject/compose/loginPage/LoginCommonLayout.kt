package com.rspk.internproject.compose.loginPage

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rspk.internproject.R
import com.rspk.internproject.compose.AppHeading
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.PageHeadingForSignUp
import com.rspk.internproject.compose.ShortInfoText
import com.rspk.internproject.compose.customComposables.CustomButton

data class InputText(
    @StringRes val text:Int,
    @StringRes val buttonText:Int,
    @StringRes val infoText:Int,
    @StringRes val infoLinkText:Int,
)

@Composable
fun LoginPagePortraitLayout(
    modifier: Modifier = Modifier,
    text: InputText,
    content: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
    onLinkButtonClick: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    buttonsEnabled :Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 30.dp)
            .padding(top = 40.dp, bottom = 20.dp),
    ) {
        AppHeading()
        Spacer(modifier = Modifier.padding(bottom = 60.dp))
        PageHeadingForSignUp(
            text = text.text,
            modifier = Modifier.padding(bottom = 25.dp)
        )
        ShortInfoText(
            text = stringResource(id = text.infoText),
            onButtonClick = {
                onLinkButtonClick()
            },
            buttonText = stringResource(id = text.infoLinkText)
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        content()
        CustomButton(
            color = R.color.red,
            state = ButtonState.ButtonWithInnerText(text = text.buttonText),
            onClick = {
                onButtonClick()
            },
            enabled = buttonsEnabled
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        bottomContent()
    }
}

@Composable
fun LoginPageLandScapeLayout(
    modifier: Modifier = Modifier,
    text: InputText,
    content: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
    onButtonClick: () -> Unit = {},
    onLinkButtonClick: () -> Unit,
    buttonsEnabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 30.dp)
            .padding(top = 40.dp, bottom = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .weight(1f)
        ) {
            AppHeading()
            Spacer(modifier = Modifier.padding(bottom = 5.dp))
            PageHeadingForSignUp(
                text = text.text,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            ShortInfoText(
                text = stringResource(id = text.infoText),
                onButtonClick = {
                    onLinkButtonClick()
                },
                buttonText = stringResource(id = text.infoLinkText )
            )
            bottomContent()
        }
        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .weight(1f),
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    content()
                }
            }
            Spacer(modifier = Modifier.weight(0.01f))
            CustomButton(
                color = R.color.red,
                state = ButtonState.ButtonWithInnerText(text = text.buttonText),
                onClick = { onButtonClick() },
                enabled = buttonsEnabled,
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}