package com.rspk.internproject.compose.customComposables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun LogoButton(
    modifier: Modifier = Modifier,
    @DrawableRes logo: Int,
    onClick: () -> Unit,
    @StringRes contentDescription: Int
){
    OutlinedButton(
        onClick = {
            onClick()
        },
        border = BorderStroke(color = Color.LightGray, width = 1.dp),
    ) {
        Image(
            painter = painterResource(id = logo),
            contentDescription = stringResource(id = contentDescription),
            modifier = Modifier
                .padding(horizontal = 7.dp, vertical = 5.dp)
                .size(30.dp)
        )
    }
}