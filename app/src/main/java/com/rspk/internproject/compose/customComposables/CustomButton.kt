package com.rspk.internproject.compose.customComposables

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.internproject.compose.ButtonState

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    @ColorRes color:Int,
    state: ButtonState,
    enabled:Boolean = true,
    onClick: () -> Unit,
    widthOfButton:Float = 1f,
){
    Button(
        enabled = enabled ,
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth(widthOfButton)
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = color),
            contentColor = Color.White,
            disabledContainerColor = colorResource(id = color),
            disabledContentColor = Color.White
        ))
    {
        when(state){
            is ButtonState.ButtonWithInnerText -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(id = state.text),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(vertical = 13.dp, horizontal = 5.dp)
                    )
                    if(!enabled){
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                }
            }
            is ButtonState.ButtonWithImage -> {
                Image(
                    painter = painterResource(id = state.image),
                    contentDescription = stringResource(id = state.contentDescription),
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}