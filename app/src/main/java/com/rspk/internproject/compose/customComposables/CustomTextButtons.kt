package com.rspk.internproject.compose.customComposables

import androidx.annotation.StringRes
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun CustomTextButtons(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes text: Int,
) {
    TextButton(
        onClick = {
            onClick()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.inverseSurface)) {
        Text(
            text = stringResource(id = text),
            textDecoration = TextDecoration.Underline,
        )
    }
}