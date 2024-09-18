package com.rspk.internproject.compose.customComposables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.internproject.R
import com.rspk.internproject.compose.ShortInfoText

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit = {},
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    trailingLambda: (@Composable () -> Unit)? = null,
    leadingLambda: (@Composable () -> Unit)? = null,
    @StringRes placeHolder: Int? = null,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions? = null,
    supportingText:String? = null
){
    TextField(
        value = value,
        isError = supportingText != null,
        onValueChange = onValueChange,
        leadingIcon = leadingLambda?:
        leadingIcon?.let {
            {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = placeHolder?.let { stringResource(id = placeHolder )}?:"",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        trailingIcon = trailingLambda?:
        trailingIcon?.let {
            {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = placeHolder?.let { stringResource(id = placeHolder )}?:"",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onTrailingIconClick()
                        }
                )
            }
        },
        supportingText = supportingText?.let{
            {
                ShortInfoText(text = "*$it", fontSize = 12.sp, textColor = colorResource(id = R.color.red))
            }
        },
        placeholder = {
            Text(text = placeHolder?.let { stringResource(id = placeHolder )}?:"")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = if (supportingText == null) 25.dp else 3.dp),
        singleLine = true,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = colorResource(id = R.color.text_field_container),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedPlaceholderColor = colorResource(id = R.color.placeholder_focused_color),
            unfocusedPlaceholderColor = colorResource(id = R.color.placeholder_unfocused_color),
            focusedTextColor = colorResource(id = R.color.placeholder_focused_color),
            errorPlaceholderColor = colorResource(id = R.color.red),
            errorLeadingIconColor = colorResource(id = R.color.red),
            errorSupportingTextColor = colorResource(id = R.color.red),
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = keyboardOptions?: when (placeHolder) {
            R.string.zip_code -> KeyboardOptions(keyboardType = KeyboardType.Number)
            R.string.phone_number -> KeyboardOptions(keyboardType = KeyboardType.Phone)
            R.string.password, R.string.re_enter_password -> KeyboardOptions(keyboardType = KeyboardType.Password)
            R.string.email -> KeyboardOptions(keyboardType = KeyboardType.Email)
            else -> KeyboardOptions.Default
        }
    )
}