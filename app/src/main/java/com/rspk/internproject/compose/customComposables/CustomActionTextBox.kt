package com.rspk.internproject.compose.customComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.internproject.R

@Composable
fun CustomActionTextBox(
    text: String,
    boxPadding:PaddingValues = PaddingValues(),
    textPadding:PaddingValues = PaddingValues(),
    textColors:Color = Color.Unspecified,
    boxColor: Color = colorResource(id = R.color.text_field_container),
    borderColor: Color = colorResource(id = R.color.text_field_container),
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(boxPadding)
            .clip(RoundedCornerShape(10.dp))
            .background(boxColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(textPadding),
            color = textColors
        )
    }
}